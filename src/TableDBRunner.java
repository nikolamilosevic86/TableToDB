import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class TableDBRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		String folder = args[0];
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		System.out.print("Hello");
		
		 try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				String connectionUrl = "jdbc:mysql://localhost:3306/table_db";
				String connectionUser = "root";
				String connectionPassword = "";
				conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);	
				
				File Path = new File(folder);
				File[] Files = Path.listFiles();
				for(int i = 0;i<Files.length;i++){
					Article art = ReadFile(Files[i]);
					StoreToSQL(art,conn);
				}
				 conn.close();
		 } catch (SQLException ex) {
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
		 }
		 finally{

		 }

	}
	
	public static void StoreToSQL(Article a,Connection conn)
	{
		try {
		Statement stmt = conn.createStatement();
		String insertTableSQL = "INSERT INTO Article (PMCID,PMID,pissn,eissn,Title,Abstract,JournalName,JournalPublisherName,JournalPublisherLocation) VALUES (?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL,Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, a.getPMCID());
		preparedStatement.setString(2, a.getPMID());
		preparedStatement.setString(3, a.getPissn());
		preparedStatement.setString(4, a.getEissn());
		preparedStatement.setString(5, a.getTitle());
		preparedStatement.setString(6, a.getAbstract());
		preparedStatement.setString(7, a.getJournalName());
		preparedStatement.setString(8, a.getJournalPublisherName());
		preparedStatement.setString(9, a.getJournalPublisherLocation());
		// execute insert SQL stetement
		int articleId = preparedStatement.executeUpdate();
		ResultSet rs1 = preparedStatement.getGeneratedKeys();
        if(rs1.next())
        {
        	articleId = rs1.getInt(1);
        }
		int authorId = -1;
		
		for(int i = 0;i<a.authors.size();i++)
		{

		    	//Add new Author to DB
		    	Statement stmt2 = conn.createStatement();
		  		String insertTableSQL2 = "INSERT INTO Author (FirstName,LastName,Article_idArticle) VALUES (?,?,?)";
		  		PreparedStatement preparedStatement2 = conn.prepareStatement(insertTableSQL2,Statement.RETURN_GENERATED_KEYS);
		  		preparedStatement2.setString(1, a.authors.get(i).getFirstName());
		  		preparedStatement2.setString(2, a.authors.get(i).getSecondName());
		  		preparedStatement2.setInt(3, articleId);
		  		// execute insert SQL stetement
		  		authorId = preparedStatement2.executeUpdate();
		  		ResultSet rs = preparedStatement2.getGeneratedKeys();
                if(rs.next())
                {
                	authorId = rs.getInt(1);
                }
		  		
                Statement stmt4 = conn.createStatement();
		  		String insertTableSQL4 = "INSERT INTO Email (Email,Author_idAuthor) VALUES (?,?)";
		  		PreparedStatement preparedStatement4 = conn.prepareStatement(insertTableSQL4,Statement.RETURN_GENERATED_KEYS);
		  		preparedStatement4.setString(1, a.authors.get(i).getEmail());
		  		preparedStatement4.setInt(2, authorId);
		  		// execute insert SQL stetement
		  		int EmailId = preparedStatement4.executeUpdate();
		  		ResultSet rs3 = preparedStatement4.getGeneratedKeys();
                if(rs3.next())
                {
                	EmailId = rs3.getInt(1);
                }
		  		
		  		LinkedList<String> affs =a.authors.get(i).AuthorAffiliation;
		  		for(int j =0;j<affs.size();j++)
		  		{
		  			Statement stmt5 = conn.createStatement();
			  		String insertTableSQL5 = "INSERT INTO Affiliation (AffiliationName,Author_idAuthor) VALUES (?,?)";
			  		PreparedStatement preparedStatement5 = conn.prepareStatement(insertTableSQL5,Statement.RETURN_GENERATED_KEYS);
			  		preparedStatement5.setString(1, affs.get(j));
			  		preparedStatement5.setInt(2, authorId);
			  		// execute insert SQL stetement
			  		int AffId = preparedStatement5.executeUpdate();
			  		ResultSet rs4 = preparedStatement5.getGeneratedKeys();
	                if(rs4.next())
	                {
	                	AffId = rs4.getInt(1);
	                }
		  		}     
		}
		LinkedList<Table> Tables = a.tables;
		for(int i = 0;i<Tables.size();i++)
		{
			Statement stmt6 = conn.createStatement();
	  		String insertTableSQL6 = "INSERT INTO ArtTable (TableOrder,TableCaption,TableFooter,StructureType,PragmaticType,HasXML,Article_idArticle) VALUES (?,?,?,?,?,?,?)";
	  		PreparedStatement preparedStatement6 = conn.prepareStatement(insertTableSQL6,Statement.RETURN_GENERATED_KEYS);
	  		preparedStatement6.setString(1,Tables.get(i).getTableOrder());
	  		preparedStatement6.setString(2, Tables.get(i).getCaption());
	  		preparedStatement6.setString(3, Tables.get(i).getFooter());
	  		preparedStatement6.setString(4, Tables.get(i).getStructureType());
	  		preparedStatement6.setString(5, Tables.get(i).getPragmaticType());
	  		preparedStatement6.setString(6, Tables.get(i).getHasXML()); //maybe change
	  		preparedStatement6.setInt(7,articleId);
	  		
	  		// execute insert SQL stetement
	  		int TableId = preparedStatement6.executeUpdate();
	  		ResultSet rs4 = preparedStatement6.getGeneratedKeys();
            if(rs4.next())
            {
            	TableId = rs4.getInt(1);
            }
            LinkedList<Cell> cells = Tables.get(i).cells;
            for(int j = 0;j<cells.size();j++)
            {
            	Statement stmt7 = conn.createStatement();
    	  		String insertTableSQL7 = "INSERT INTO Cell (CellID,CellType,Table_idTable,RowN,ColumnN,HeaderRef,StubRef,SuperRowRef,Content) VALUES (?,?,?,?,?,?,?,?,?)";
    	  		PreparedStatement preparedStatement7 = conn.prepareStatement(insertTableSQL7,Statement.RETURN_GENERATED_KEYS);
    	  		preparedStatement7.setString(1,cells.get(j).getCellID());
    	  		preparedStatement7.setString(2, cells.get(j).getCellType());
    	  		preparedStatement7.setInt(3,TableId);
    	  		preparedStatement7.setString(4, cells.get(j).getRowN());
    	  		preparedStatement7.setString(5, cells.get(j).getColumnN());
    	  		preparedStatement7.setString(6, cells.get(j).getHeaderRef()); //maybe change
    	  		preparedStatement7.setString(7,cells.get(j).getStubRef());
    	  		preparedStatement7.setString(8,cells.get(j).getSuperRowRef());
    	  		preparedStatement7.setString(9,cells.get(j).getCellValue());

    	  		
    	  		// execute insert SQL stetement
    	  		int CellId = preparedStatement7.executeUpdate();
    	  		ResultSet rs5 = preparedStatement7.getGeneratedKeys();
                if(rs5.next())
                {
                	CellId = rs5.getInt(1);
                }
    	  		
    	  		for(int k=0;k<cells.get(j).CellRole.size();k++)
    	  		{
	  				String insertTableSQL8 = "INSERT INTO CellRoles (CellRole_idCellRole,Cell_idCell) VALUES (?,?)";
	    	  		PreparedStatement preparedStatement8 = conn.prepareStatement(insertTableSQL8,Statement.RETURN_GENERATED_KEYS);
    	  			if(cells.get(j).CellRole.get(k).equals("Header"))
    	  			{
    	    	  		preparedStatement8.setInt(1,1);
    	  			}
    	  			if(cells.get(j).CellRole.get(k).equals("Stub"))
    	  			{
    	    	  		preparedStatement8.setInt(1,2);
    	  			}
    	  			if(cells.get(j).CellRole.get(k).equals("Data"))
    	  			{
    	    	  		preparedStatement8.setInt(1,3);
    	  			}
    	  			if(cells.get(j).CellRole.get(k).equals("SuperRow"))
    	  			{
    	    	  		preparedStatement8.setInt(1,4);
    	  			}
	    	  		preparedStatement8.setInt(2, CellId);
	    	  		preparedStatement8.executeUpdate();
    	  			
    	  		}
    	  		
    	  		LinkedList<Annotation> annot = cells.get(j).annotations;
    	  		for(int k = 0; k<annot.size();k++)
    	  		{
    	  			Statement stmt8 = conn.createStatement();
        	  		String insertTableSQL8 = "INSERT INTO Annotation (Content,Start,End,ID,Type,TypeVal,URL,Source,Cell_idCell) VALUES (?,?,?,?,?,?,?,?,?)";
        	  		PreparedStatement preparedStatement8 = conn.prepareStatement(insertTableSQL8,Statement.RETURN_GENERATED_KEYS);
        	  		preparedStatement8.setString(1,annot.get(k).getContent());
        	  		preparedStatement8.setInt(2,annot.get(k).getStart());
        	  		preparedStatement8.setInt(3,annot.get(k).getEnd());
        	  		preparedStatement8.setString(4,annot.get(k).getID());
        	  		preparedStatement8.setString(5,annot.get(k).getType());
        	  		preparedStatement8.setString(6,annot.get(k).getTypeVal());
        	  		preparedStatement8.setString(7,annot.get(k).getURL());
        	  		preparedStatement8.setString(8,annot.get(k).getSource());
        	  		preparedStatement8.setInt(9,CellId);
        	  		// execute insert SQL stetement
        	  		preparedStatement8.executeUpdate();
    	  		}
                
            }
		}
		}
		catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
	 }
	 catch(Exception ex)
	 {
		 ex.printStackTrace();
	 }
	}
	
	public static Article ReadFile(File FileName)
	{
		Article article = new Article();
		LinkedList<Table> tablesList = new LinkedList<Table>();
		try{
		BufferedReader reader = new BufferedReader(new FileReader(FileName));
		String line = null;
		String xml = "";
		while ((line = reader.readLine()) != null) {
		    xml +=line+'\n';
		}		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		factory.setNamespaceAware(true);
		factory.setValidating(false);
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    InputSource is = new InputSource(new StringReader(xml));
	    Document parse =  builder.parse(is);
	    
	    
	    NodeList PMCID = parse.getElementsByTagName("PMCID");
	    article.setPMCID(PMCID.item(0).getTextContent());
	    NodeList PMID = parse.getElementsByTagName("PMID");
	    article.setPMID(PMID.item(0).getTextContent());
	    NodeList PISSN = parse.getElementsByTagName("p-issn");
	    article.setPissn(PISSN.item(0).getTextContent());
	    NodeList EISSN = parse.getElementsByTagName("e-issn");
	    article.setPissn(EISSN.item(0).getTextContent());
	    NodeList Title = parse.getElementsByTagName("Title");
	    article.setTitle(Title.item(0).getTextContent());
	    NodeList Abstract = parse.getElementsByTagName("Abstract");
	    article.setAbstract(Abstract.item(0).getTextContent());
	    NodeList journal = parse.getElementsByTagName("journal");
	    article.setJournalName(journal.item(0).getTextContent());
	    NodeList PublisherName = parse.getElementsByTagName("PublisherName");
	    article.setJournalPublisherName(PublisherName.item(0).getTextContent());
	    NodeList PublisherLocation = parse.getElementsByTagName("PublisherLocation");
	    article.setJournalPublisherLocation(PublisherLocation.item(0).getTextContent());
	    
	    NodeList Authors = parse.getElementsByTagName("Author");
	    for(int i = 0;i<Authors.getLength();i++)
	    {
	    	Author a = new Author();
	    	List<Node> Author = getChildrenByTagName(Authors.item(i),"AuthorName");
	    	String AuthorName = Author.get(0).getTextContent();
	    	String[] AuthorFirstSecondName = AuthorName.split(",");
	    	a.setAuthorName(AuthorName);
	    	if(AuthorFirstSecondName.length>1)
	    		a.setFirstName(AuthorFirstSecondName[1]);
	    	a.setSecondName(AuthorFirstSecondName[0]);
	    	List<Node> Affiliation = getChildrenByTagName(Authors.item(i),"AuthorAffiliation");
	    	for(int j = 0;j<Affiliation.size();j++)
	    	{
	    		a.AuthorAffiliation.add(Affiliation.get(j).getTextContent());
	    	}	
	    	List<Node> Email = getChildrenByTagName(Authors.item(i),"AuthorEmail");
	    	for(int j = 0;j<Email.size();j++)
	    	{
	    		a.setEmail(Email.get(j).getTextContent());
	    	}
	    	article.authors.add(a);
	    }
	    
	    
	    
	    NodeList tables = parse.getElementsByTagName("Table");		    
	    System.out.println(tables.getLength());
	    for(int j = 0;j<tables.getLength();j++)
	    {
	    	Table tmp_table = new Table();
	    	tmp_table.setFileName(FileName.getName());
	    	List<Node> TableOrder = getChildrenByTagName(tables.item(j),"TableOrder");
	    	tmp_table.setTableOrder(TableOrder.get(0).getTextContent());
	    	List<Node> TableCaption = getChildrenByTagName(tables.item(j),"TableCaption");
	    	tmp_table.setCaption(TableCaption.get(0).getTextContent());
	    	List<Node> TableFooter = getChildrenByTagName(tables.item(j),"TableFooter");
	    	tmp_table.setFooter(TableFooter.get(0).getTextContent());
	    	List<Node> TableStructureType = getChildrenByTagName(tables.item(j),"TableStructureType");
	    	tmp_table.setStructureType(TableStructureType.get(0).getTextContent());
	    	List<Node> TablePragmaticClass = getChildrenByTagName(tables.item(j),"TablePragmaticClass");
	    	tmp_table.setPragmaticType(TablePragmaticClass.get(0).getTextContent());
	    	List<Node> hasXML = getChildrenByTagName(tables.item(j),"TabHasXML");
	    	tmp_table.setHasXML(hasXML.get(0).getTextContent());
	    	System.out.println(tmp_table.getStructureType());
	    	tablesList.add(tmp_table);
	    	List<Node> cells = getChildrenByTagName(tables.item(j),"Cells");
	    	for(int k=0;k<cells.size();k++)
	    	{
	    		List<Node> cell = getChildrenByTagName(cells.get(k),"Cell");
	    		for(int l=0;l<cell.size();l++)
		    	{
	    			Cell cellItem = new Cell();
	    			List<Node> CellID = getChildrenByTagName(cell.get(l),"CellID");
	    			cellItem.setCellID(CellID.get(0).getTextContent());
	    			List<Node> CellValue = getChildrenByTagName(cell.get(l),"CellValue");
	    			cellItem.setCellValue(CellValue.get(0).getTextContent());
	    			List<Node> CellType = getChildrenByTagName(cell.get(l),"CellType");
	    			if(CellType!=null&&CellType.size()>0){
	    				cellItem.setCellType(CellType.get(0).getTextContent());
	    			}
	    			List<Node> HeaderRef = getChildrenByTagName(cell.get(l),"HeaderRef");
	    			if(HeaderRef!=null&&HeaderRef.size()>0){
	    				cellItem.setHeaderRef(HeaderRef.get(0).getTextContent());
	    			}
	    			List<Node> CellRowNum = getChildrenByTagName(cell.get(l),"CellRowNum");
	    			if(CellRowNum!=null&&CellRowNum.size()>0){
	    				cellItem.setRowN(CellRowNum.get(0).getTextContent());
	    			}
	    			List<Node> CellColumnNum = getChildrenByTagName(cell.get(l),"CellColumnNum");
	    			if(CellColumnNum!=null&&CellColumnNum.size()>0){
	    				cellItem.setColumnN(CellColumnNum.get(0).getTextContent());
	    			}
	    			List<Node> StubRef = getChildrenByTagName(cell.get(l),"StubRef");
	    			if(StubRef!=null&&StubRef.size()>0){
	    				cellItem.setStubRef(StubRef.get(0).getTextContent());
	    			}
	    			List<Node> HeadStubRef = getChildrenByTagName(cell.get(l),"HeadStubRef");
	    			if(HeadStubRef!=null&&HeadStubRef.size()>0){
	    				cellItem.setStubHeadRef(HeadStubRef.get(0).getTextContent());
	    			}
	    			List<Node> SuperRowRef = getChildrenByTagName(cell.get(l),"SuperRowRef");
	    			if(SuperRowRef!=null&&SuperRowRef.size()>0){
	    				cellItem.setSuperRowRef(SuperRowRef.get(0).getTextContent());
	    				
	    			}
	    			List<Node> CellRoles = getChildrenByTagName(cell.get(l),"CellRoles");
    				LinkedList<String> cellRolesList = new LinkedList<String>();
	    			for(int o = 0;o<CellRoles.size();o++){
	    				List<Node> CellRole = getChildrenByTagName(CellRoles.get(o),"CellRole");
	    				String CellRoleStr = CellRole.get(0).getTextContent();
	    				cellRolesList.add(CellRoleStr);
	    			}
	    			cellItem.setCellRole(cellRolesList);
	    			
	    			List<Node> CellAnnotations = getChildrenByTagName(cell.get(l),"Annotations");
					for (int o = 0; o < CellAnnotations.size(); o++) {
						List<Node> CellAnnotation = getChildrenByTagName(CellAnnotations.get(o), "Annotation");
						Annotation annotat = new Annotation();
						for (int s = 0; s < CellAnnotation.size(); s++) {
							NamedNodeMap attributes = CellAnnotation.get(0).getAttributes();
							if (null != attributes) {
								annotat.setContent(attributes.getNamedItem("Content").getNodeValue());
								annotat.setStart(Integer.parseInt(attributes.getNamedItem("Start").getNodeValue()));
								annotat.setEnd(Integer.parseInt(attributes.getNamedItem("End").getNodeValue()));
								annotat.setID(attributes.getNamedItem("ID").getNodeValue());
								annotat.setSource(attributes.getNamedItem("Source").getNodeValue());
								annotat.setType(attributes.getNamedItem("Type").getNodeValue());
								annotat.setTypeVal(attributes.getNamedItem("TypeVal").getNodeValue());
								annotat.setURL(attributes.getNamedItem("URL").getNodeValue());
								cellItem.annotations.add(annotat);
								}
							}
						}
	    			
	    			
	    			
	    			tmp_table.cells.add(cellItem);
	    			

		    	}
	    	}
	    	
	    }
	    
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		article.tables = tablesList;
		return article;
	}
	
	/**
	 * Gets the children by tag name.
	 *
	 * @param parent the parent
	 * @param name the name
	 * @return the children by tag name
	 */
	public static List<Node> getChildrenByTagName(Node parent, String name) {
	    List<Node> nodeList = new ArrayList<Node>();
	    for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
	      if (child.getNodeType() == Node.ELEMENT_NODE && 
	          name.equals(child.getNodeName())) {
	        nodeList.add((Node) child);
	      }
	    }

	    return nodeList;
	  }
}
