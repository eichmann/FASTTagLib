package edu.uiowa.slis.FAST.utility;

import java.io.File;
import java.io.IOException;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.Syntax;
import org.apache.jena.tdb.TDBFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;

import edu.uiowa.slis.FAST.TagLibSupport;

public class Indexer {
    protected static Logger logger = Logger.getLogger(Indexer.class);
    static TagLibSupport theTag = new TagLibSupport();
    
    static boolean useSPARQL = false;
    static Dataset dataset = null;
    static String tripleStore = null;
    static String endpoint = null;

    static String dataPath = "/usr/local/RAID/";
    static String lucenePath = "/usr/local/RAID/LD4L/lucene/fast/persons";
    static String prefix = 
	    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
	    + " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
	    + " PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
	    + " PREFIX mads: <http://www.loc.gov/mads/rdf/v1#> "
	    + " PREFIX skos: <http://www.w3.org/2004/02/skos/core#> "
	    + " PREFIX bib: <http://bib.ld4l.org/ontology/> "
    	    + " PREFIX schema: <http://schema.org/> ";



    
    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws CorruptIndexException, LockObtainFailedException, IOException {
	PropertyConfigurator.configure("log4j.info");

	tripleStore = dataPath + args[0];
	endpoint = "http://services.ld4l.org/fuseki/" + args[0] + "/sparql";

	if (args.length > 0 && args[1].equals("work"))
	    lucenePath = "/usr/local/RAID/LD4L/lucene/" + "fast" + "/" + args[1];
	if (args.length > 0 && args[1].equals("person"))
	    lucenePath = "/usr/local/RAID/LD4L/lucene/" + "fast" + "/" + args[1];
	if (args.length > 0 && args[1].equals("organization"))
	    lucenePath = "/usr/local/RAID/LD4L/lucene/" + "fast" + "/" + args[1];
	if (args.length > 0 && args[1].equals("place"))
	    lucenePath = "/usr/local/RAID/LD4L/lucene/" + "fast" + "/" + args[1];
	if (args.length > 0 && args[1].equals("intangible"))
	    lucenePath = "/usr/local/RAID/LD4L/lucene/" + "fast" + "/" + args[1];
//	if (args.length > 0 && args[1].equals("geo"))
//	    lucenePath = "/usr/local/RAID/LD4L/lucene/" + "fast" + "/" + args[1];
	if (args.length > 0 && args[1].equals("concept"))
	    lucenePath = "/usr/local/RAID/LD4L/lucene/" + "fast" + "/" + args[1];
	if (args.length > 0 && args[1].equals("genre"))
	    lucenePath = "/usr/local/RAID/LD4L/lucene/" + "fast" + "/" + args[1];
	if (args.length > 0 && args[1].equals("event"))
	    lucenePath = "/usr/local/RAID/LD4L/lucene/" + "fast" + "/" + args[1];

	IndexWriter theWriter = new IndexWriter(FSDirectory.open(new File(lucenePath)), new StandardAnalyzer(org.apache.lucene.util.Version.LUCENE_30), true, IndexWriter.MaxFieldLength.UNLIMITED);

	if (args.length > 0 && args[1].equals("work"))
	    indexWorkTitles(theWriter);
	if (args.length > 0 && args[1].equals("person"))
	    indexPersons(theWriter);
	if (args.length > 0 && args[1].equals("organization"))
	    indexOrganizations(theWriter);
	if (args.length > 0 && args[1].equals("place"))
	    indexPlaces(theWriter);
//	if (args.length > 0 && args[1].equals("geo"))
//	    indexGeos(theWriter);
	if (args.length > 0 && args[1].equals("intangible"))
	    indexIntangibles(theWriter);
	if (args.length > 0 && args[1].equals("concept"))
	    indexConcepts(theWriter);
	if (args.length > 0 && args[1].equals("genre"))
	    indexGenre(theWriter);
	if (args.length > 0 && args[1].equals("event"))
	    indexEvents(theWriter);

	logger.info("optimizing index...");
	theWriter.optimize();
	theWriter.close();
    }
    
    static void indexWorkTitles(IndexWriter theWriter) throws CorruptIndexException, IOException {
	int count = 0;
	String query =
		"SELECT DISTINCT ?work ?title WHERE { "
		+ "?work rdf:type <http://schema.org/CreativeWork> . "
		+ "?work <http://schema.org/name> ?title . "
    		+ "}";
	ResultSet rs = getResultSet(prefix + query);
	while (rs.hasNext()) {
	    QuerySolution sol = rs.nextSolution();
	    String work = sol.get("?work").toString();
	    String title = sol.get("?title").asLiteral().getString();
	    logger.info("work: " + work + "\ttitle: " + title);
	    
	    Document theDocument = new Document();
	    theDocument.add(new Field("uri", work, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("title", title, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("content", title, Field.Store.NO, Field.Index.ANALYZED));
	    theWriter.addDocument(theDocument);
	    count++;
	    if (count % 10000 == 0)
		logger.info("count: " + count);
	}
	logger.info("total titles: " + count);
    }
    
    static void indexOrganizations(IndexWriter theWriter) throws CorruptIndexException, IOException {
	int count = 0;
	String query =
		"SELECT DISTINCT ?organization ?title WHERE { "
		+ "?organization rdf:type <http://schema.org/Organization> . "
		+ "?organization <http://schema.org/name> ?title . "
    		+ "}";
	ResultSet rs = getResultSet(prefix + query);
	while (rs.hasNext()) {
	    QuerySolution sol = rs.nextSolution();
	    String organization = sol.get("?organization").toString();
	    String title = sol.get("?title").asLiteral().getString();
	    logger.info("organization: " + organization + "\ttitle: " + title);
	    
	    Document theDocument = new Document();
	    theDocument.add(new Field("uri", organization, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("title", title, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("content", title, Field.Store.NO, Field.Index.ANALYZED));
	    theWriter.addDocument(theDocument);
	    count++;
	    if (count % 10000 == 0)
		logger.info("count: " + count);
	}
	logger.info("total titles: " + count);
    }
    
    // this currently only involves blank nodes, so we're suppressing generation and access
    static void indexGeos(IndexWriter theWriter) throws CorruptIndexException, IOException {
	int count = 0;
	String query =
		"SELECT DISTINCT ?geo ?title WHERE { "
		+ "?geo rdf:type <http://schema.org/GeoCoordinates> . "
		+ "?geo <http://schema.org/name> ?title . "
    		+ "}";
	ResultSet rs = getResultSet(prefix + query);
	while (rs.hasNext()) {
	    QuerySolution sol = rs.nextSolution();
	    String geo = sol.get("?geo").toString();
	    String title = sol.get("?title").asLiteral().getString();
	    logger.info("geo: " + geo + "\ttitle: " + title);
	    
	    Document theDocument = new Document();
	    theDocument.add(new Field("uri", geo, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("title", title, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("content", title, Field.Store.NO, Field.Index.ANALYZED));
	    theWriter.addDocument(theDocument);
	    count++;
	    if (count % 10000 == 0)
		logger.info("count: " + count);
	}
	logger.info("total titles: " + count);
    }
    
    static void indexIntangibles(IndexWriter theWriter) throws CorruptIndexException, IOException {
	int count = 0;
	String query =
		"SELECT DISTINCT ?intangible ?title WHERE { "
		+ "?intangible rdf:type <http://schema.org/Intangible> . "
		+ "?intangible <http://schema.org/name> ?title . "
    		+ "}";
	ResultSet rs = getResultSet(prefix + query);
	while (rs.hasNext()) {
	    QuerySolution sol = rs.nextSolution();
	    String intangible = sol.get("?intangible").toString();
	    String title = sol.get("?title").asLiteral().getString();
	    logger.info("intangible: " + intangible + "\ttitle: " + title);
	    
	    Document theDocument = new Document();
	    theDocument.add(new Field("uri", intangible, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("title", title, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("content", title, Field.Store.NO, Field.Index.ANALYZED));
	    theWriter.addDocument(theDocument);
	    count++;
	    if (count % 10000 == 0)
		logger.info("count: " + count);
	}
	logger.info("total titles: " + count);
    }
    
    static void indexPlaces(IndexWriter theWriter) throws CorruptIndexException, IOException {
	int count = 0;
	String query =
		"SELECT DISTINCT ?place ?title WHERE { "
		+ "?place rdf:type <http://schema.org/Place> . "
		+ "?place <http://schema.org/name> ?title . "
    		+ "}";
	ResultSet rs = getResultSet(prefix + query);
	while (rs.hasNext()) {
	    QuerySolution sol = rs.nextSolution();
	    String place = sol.get("?place").toString();
	    String title = sol.get("?title").asLiteral().getString();
	    logger.info("palce: " + place + "\ttitle: " + title);
	    
	    Document theDocument = new Document();
	    theDocument.add(new Field("uri", place, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("title", title, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("content", title, Field.Store.NO, Field.Index.ANALYZED));
	    theWriter.addDocument(theDocument);
	    count++;
	    if (count % 10000 == 0)
		logger.info("count: " + count);
	}
	logger.info("total titles: " + count);
    }
    
    static void indexPersons(IndexWriter theWriter) throws CorruptIndexException, IOException {
	int count = 0;
	String query =
		"SELECT DISTINCT ?uri ?name WHERE { "
			+ "?uri rdf:type <http://schema.org/Person> . "
			+ "?uri <http://schema.org/name> ?name . "
    		+ "} ";
	ResultSet rs = getResultSet(prefix + query);
	while (rs.hasNext()) {
	    QuerySolution sol = rs.nextSolution();
//	    String authorityURI = sol.get("?uri").toString();
	    String personURI = sol.get("?uri").toString();
	    String name = sol.get("?name").asLiteral().getString();
	    logger.info("uri: " + personURI + "\tname: " + name);
	    
	    Document theDocument = new Document();
	    theDocument.add(new Field("uri", personURI, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("name", name, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("content", name, Field.Store.NO, Field.Index.ANALYZED));
	    theWriter.addDocument(theDocument);
	    count++;
	    if (count % 10000 == 0)
		logger.info("count: " + count);
	}
	logger.info("total titles: " + count);
    }

    static void indexConcepts(IndexWriter theWriter) throws CorruptIndexException, IOException {
	int count = 0;
	String query =
		"SELECT DISTINCT ?concept ?label WHERE { "
		+ "?concept rdf:type schema:Intangible . "
		+ "?concept skos:inScheme <http://id.worldcat.org/fast/ontology/1.0/#facet-Topical> . "
		+ "?concept skos:prefLabel ?label . "
    		+ "}";
	ResultSet rs = getResultSet(prefix + query);
	while (rs.hasNext()) {
	    QuerySolution sol = rs.nextSolution();
	    String concept = sol.get("?concept").toString();
	    String label = sol.get("?label").asLiteral().getString();
	    logger.info("concept: " + concept + "\tlabel: " + label);
	    
	    Document theDocument = new Document();
	    theDocument.add(new Field("uri", concept, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("title", label, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("content", label, Field.Store.NO, Field.Index.ANALYZED));
	    
	    annotateEntity(theDocument, concept);
	    
	    theWriter.addDocument(theDocument);
	    count++;
	    if (count % 10000 == 0)
		logger.info("count: " + count);
	}
	logger.info("total titles: " + count);
    }
    
    static void indexGenre(IndexWriter theWriter) throws CorruptIndexException, IOException {
	int count = 0;
	String query =
		"SELECT DISTINCT ?concept ?label WHERE { "
		+ "?concept rdf:type schema:Intangible . "
		+ "?concept skos:inScheme <http://id.worldcat.org/fast/ontology/1.0/#facet-FormGenre> . "
		+ "?concept skos:prefLabel ?label . "
    		+ "}";
	ResultSet rs = getResultSet(prefix + query);
	while (rs.hasNext()) {
	    QuerySolution sol = rs.nextSolution();
	    String concept = sol.get("?concept").toString();
	    String label = sol.get("?label").asLiteral().getString();
	    logger.info("concept: " + concept + "\tlabel: " + label);
	    
	    Document theDocument = new Document();
	    theDocument.add(new Field("uri", concept, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("title", label, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("content", label, Field.Store.NO, Field.Index.ANALYZED));
	    
	    annotateEntity(theDocument, concept);
	    
	    theWriter.addDocument(theDocument);
	    count++;
	    if (count % 10000 == 0)
		logger.info("count: " + count);
	}
	logger.info("total titles: " + count);
    }
    
    static void indexEvents(IndexWriter theWriter) throws CorruptIndexException, IOException {
	int count = 0;
	String query =
		"SELECT DISTINCT ?event ?label WHERE { "
		+ "?event rdf:type <http://schema.org/Event> . "
		+ "?event <http://www.w3.org/2004/02/skos/core#prefLabel> ?label . "
    		+ "}";
	ResultSet rs = getResultSet(prefix + query);
	while (rs.hasNext()) {
	    QuerySolution sol = rs.nextSolution();
	    String event = sol.get("?event").toString();
	    String label = sol.get("?label").asLiteral().getString();
	    logger.info("event: " + event + "\tlabel: " + label);
	    
	    Document theDocument = new Document();
	    theDocument.add(new Field("uri", event, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("title", label, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("content", label, Field.Store.NO, Field.Index.ANALYZED));
	    
	    annotateEntity(theDocument, event);
	    
	    theWriter.addDocument(theDocument);
	    count++;
	    if (count % 10000 == 0)
		logger.info("count: " + count);
	}
	logger.info("total titles: " + count);
    }
    
    static public void annotateEntity(Document theDocument, String entity) {
	    String query1 = 
		  "SELECT DISTINCT ?name WHERE { "
			  + "<" + entity + "> <http://schema.org/name> ?name . "
		+ "}";
	    ResultSet prs = getResultSet(prefix + query1);
	    while (prs.hasNext()) {
		QuerySolution psol = prs.nextSolution();
		String name = psol.get("?name").asLiteral().getString();
		logger.info("\tname: " + name);
		theDocument.add(new Field("content", name, Field.Store.NO, Field.Index.ANALYZED));
	    }
	    
	    String query2 = 
		  "SELECT DISTINCT ?altlabel WHERE { "
			  + "<" + entity + "> skos:altLabel ?altlabel . "
		+ "}";
	    ResultSet ars = getResultSet(prefix + query2);
	    while (ars.hasNext()) {
		QuerySolution asol = ars.nextSolution();
		String altlabel = asol.get("?altlabel").asLiteral().getString();
		logger.info("\talt label: " + altlabel);
		theDocument.add(new Field("content", altlabel, Field.Store.NO, Field.Index.ANALYZED));
	    }
    }
    
    static public ResultSet getResultSet(String queryString) {
	if (useSPARQL) {
	    Query theClassQuery = QueryFactory.create(queryString, Syntax.syntaxARQ);
	    QueryExecution theClassExecution = QueryExecutionFactory.sparqlService(endpoint, theClassQuery);
	    return theClassExecution.execSelect();
	} else {
	    dataset = TDBFactory.createDataset(tripleStore);
	    Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
	    QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
	    return qexec.execSelect();
	}
    }
}
