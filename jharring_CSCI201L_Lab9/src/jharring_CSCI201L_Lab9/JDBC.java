package jharring_CSCI201L_Lab9;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class JDBC {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/lab9?user=root&password=AdInfinitum2!");
			st = conn.createStatement();
			String pre_q = "SELECT s.SID, Name, Grade, ClassName"
					+ " FROM StudentInfo s"
					+ " LEFT JOIN Grades g"
					+ " ON s.SID = g.SID"
					+ " ORDER BY ClassName ASC,"
					+ "		s.SID ASC;";
			rs = st.executeQuery(pre_q);
//			TreeMap<String, Integer> table1 = new TreeMap<String, Integer>();
			ArrayList<School> classes = new ArrayList<School>();
			while(rs.next()) {
				boolean found = false;
				String class_name = rs.getString("ClassName");
				for(School class_ : classes) {
					if(class_.name.equals(class_name)) {
						found = true;
						class_.count++;
					}
				}
				if(!found)
					classes.add(new School(class_name));
				found = false;
			}
			int index = 0; int prints = 0;
			System.out.println("ClassName\tNumber of Students");
			while(++index > 0) {
				for(int i=0; i<classes.size();i++) {
					if(classes.get(i).count == index) {
						String name = classes.get(i).name;
						int count = classes.get(i).count;
						System.out.println(name + "\t\t" + count);
						prints++;
					}
					if(prints == classes.size())
						break;
				}
				if(prints == classes.size())
					break;
			}
			
			rs = st.executeQuery("SELECT s.SID, ID, Name, Grade, ClassName "
					+ "FROM StudentInfo s "
					+ "LEFT JOIN Grades g "
					+ "ON s.SID = g.SID "
					+ "ORDER BY s.SID ASC, ClassName ASC;");
			System.out.println("\n\nClassName\tName\t\t\tGrade");
			while(rs.next()) {
				String output = rs.getString("ClassName") + "\t\t";
				output += rs.getString("Name") + "\t\t";
				if(rs.getString("Name").equals("Jack Xu"))
					output += "\t";
				output += rs.getString("Grade");
				System.out.println(output);
			}
		} catch(SQLException sqle){
			System.out.println(sqle.getMessage());
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(st != null)
					st.close();
				if(conn != null)
					conn.close();
			} catch(SQLException sqle){
				System.out.println(sqle.getMessage());
			}
		}
	}

}
