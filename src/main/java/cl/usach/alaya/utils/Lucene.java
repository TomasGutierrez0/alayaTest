package cl.usach.alaya.utils;

import java.io.IOException;

import cl.usach.alaya.model.Idea;
import cl.usach.alaya.model.Challenge;
import cl.usach.alaya.model.Comment;
import cl.usach.alaya.repository.IdeaRepository;
import cl.usach.alaya.repository.ChallengeRepository;
import cl.usach.alaya.repository.CommentRepository;
import cl.usach.alaya.utils.Constant;
import cl.usach.alaya.utils.Util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class Lucene implements ApplicationRunner {
    private final static Logger LOGGER = Logger.getLogger(Lucene.class.getName());

    private static IdeaRepository ideaRepository;
    private static ChallengeRepository challengeRepository;
    private static CommentRepository commentRepository;

    @Autowired
    public Lucene(IdeaRepository ideaRepository, ChallengeRepository challengeRepository, CommentRepository commentRepository) {
        Lucene.ideaRepository = ideaRepository;
        Lucene.challengeRepository = challengeRepository;
        Lucene.commentRepository = commentRepository;
    }

    public void run(ApplicationArguments args) {
        createIndex();
    }

    @Scheduled(cron = "0 0 * * * *") // Cada hora
    public void createIndex() {
        Path lucenePath = Paths.get(Lucene.class.getResource("").getPath()+"/lucene/");
        System.out.println(lucenePath);
        try {
            Directory dir = FSDirectory.open(lucenePath);

            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

            IndexWriter writer = new IndexWriter(dir,iwc);

            List<Idea> ideas = ideaRepository.findAll();
            List<Challenge> challenges = challengeRepository.findAll();
            List<Comment> comments = commentRepository.findAll();
            org.apache.lucene.document.Document docLucene = new org.apache.lucene.document.Document();
            for (Idea idea: ideas){
                docLucene.add(new TextField(Constant.IDEAID_FIELD, idea.get_id(), Field.Store.YES));
                docLucene.add(new TextField(Constant.IDEATEXT_FIELD, idea.getText(), Field.Store.YES));
                docLucene.add(new TextField(Constant.IDEATITTLE_FIELD, idea.getTitle(), Field.Store.YES));
                //docLucene.add(new TextField(Constant.IDEACATEGORY_FIELD, idea.getCategory(), Field.Store.YES);
            }
            for (Challenge challenge: challenges){
                docLucene.add(new TextField(Constant.CHALLENGEID_FIELD, challenge.get_id(),Field.Store.YES));
                docLucene.add(new TextField(Constant.CHALLENGETEXT_FIELD, challenge.getText(), Field.Store.YES));
                docLucene.add(new TextField(Constant.CHALLENGETITTLE_FIELD, challenge.getTitle(), Field.Store.YES));
                docLucene.add(new TextField(Constant.CHALLENGEINTEREST_FIELD, challenge.getInterestArea(), Field.Store.YES));
            }
            for (Comment comment: comments){
                docLucene.add(new TextField(Constant.COMMENTID_FIELD, comment.get_id(), Field.Store.YES));
                docLucene.add(new TextField(Constant.COMMENTTEXT_FIELD, comment.getText(), Field.Store.YES));
            }

            if (writer.getConfig().getOpenMode() == IndexWriterConfig.OpenMode.CREATE) {
                writer.addDocument(docLucene);
            } else {
                //No se que hace este else
                //writer.updateDocument(new Term("path" + occurrence.toString()), docLucene);
            }


            writer.close();
            LOGGER.log(Level.INFO, "Lucene created successfully!");
        } catch(IOException ioe) {
            LOGGER.log(Level.WARNING, "Caught a " + ioe.getClass() + " with message: " + ioe.getMessage());
        }

    }

    public static List<Idea> searchIdea(String query) {
        Path lucenePath = Paths.get(Lucene.class.getResource("").getPath()+"/lucene/");
        List<Idea> ideas = new ArrayList<>();
        query = Util.clean(query);
        try {
            IndexReader reader = DirectoryReader.open(FSDirectory.open(lucenePath));
            IndexSearcher searcher = new IndexSearcher(reader);
            StandardAnalyzer analyzer = new StandardAnalyzer();
            TopScoreDocCollector collector = TopScoreDocCollector.create(50);

            Query luceneQuery;
            BooleanQuery.Builder finalQuery = new BooleanQuery.Builder();
            if(query.length() > 0) {
                luceneQuery = new QueryParser(Constant.IDEATEXT_FIELD, analyzer).parse(query);
                finalQuery.add(luceneQuery, BooleanClause.Occur.MUST);
            }
            if(query.length() > 0) {
                luceneQuery = new QueryParser(Constant.IDEATITTLE_FIELD, analyzer).parse(query);
                finalQuery.add(luceneQuery, BooleanClause.Occur.MUST);
            }


            searcher.search(finalQuery.build(), collector);
            ScoreDoc[] hits = collector.topDocs().scoreDocs;

            LOGGER.log(Level.INFO, "Found " + hits.length + " hits.");
            for (ScoreDoc hit : hits) {
                Document document = searcher.doc(hit.doc);
                String ideaId = document.get(Constant.IDEAID_FIELD);
                Idea idea = ideaRepository.findById(ideaId).get();
                ideas.add(idea);
            }

            reader.close();
        } catch(IOException | ParseException ex) {
            ex.printStackTrace();
        }
        System.out.println(ideas);
        return ideas;
    }

    public static List<Challenge> searchChallenge(String query, String interest) {
        Path lucenePath = Paths.get(Lucene.class.getResource("").getPath()+"/lucene/");
        List<Challenge> challenges = new ArrayList<>();
        query = Util.clean(query);
        try {
            IndexReader reader = DirectoryReader.open(FSDirectory.open(lucenePath));
            IndexSearcher searcher = new IndexSearcher(reader);
            StandardAnalyzer analyzer = new StandardAnalyzer();
            TopScoreDocCollector collector = TopScoreDocCollector.create(50);

            Query luceneQuery;
            BooleanQuery.Builder finalQuery = new BooleanQuery.Builder();
            if(query.length() > 0) {
                luceneQuery = new QueryParser(Constant.COMMENTTEXT_FIELD, analyzer).parse(query);
                finalQuery.add(luceneQuery, BooleanClause.Occur.MUST);
            }
            if(query.length() > 0) {
                luceneQuery = new QueryParser(Constant.CHALLENGETITTLE_FIELD, analyzer).parse(query);
                finalQuery.add(luceneQuery, BooleanClause.Occur.MUST);
            }
            if(query.length() > 0) {
                luceneQuery = new QueryParser(Constant.CHALLENGEINTEREST_FIELD, analyzer).parse(interest);
                finalQuery.add(luceneQuery, BooleanClause.Occur.MUST);
            }

            searcher.search(finalQuery.build(), collector);
            ScoreDoc[] hits = collector.topDocs().scoreDocs;

            LOGGER.log(Level.INFO, "Found " + hits.length + " hits.");
            for (ScoreDoc hit : hits) {
                Document document = searcher.doc(hit.doc);
                String challengeId = document.get(Constant.CHALLENGEID_FIELD);
                Challenge challenge = challengeRepository.findById(challengeId).get();
                challenges.add(challenge);
            }

            reader.close();
        } catch(IOException | ParseException ex) {
            ex.printStackTrace();
        }
        return challenges;
    }

    public static List<Comment> searchComment(String query) {
        Path lucenePath = Paths.get(Lucene.class.getResource("").getPath()+"/lucene/");
        List<Comment> comments = new ArrayList<>();
        query = Util.clean(query);
        try {
            IndexReader reader = DirectoryReader.open(FSDirectory.open(lucenePath));
            IndexSearcher searcher = new IndexSearcher(reader);
            StandardAnalyzer analyzer = new StandardAnalyzer();
            TopScoreDocCollector collector = TopScoreDocCollector.create(50);

            Query luceneQuery;
            BooleanQuery.Builder finalQuery = new BooleanQuery.Builder();
            if(query.length() > 0) {
                luceneQuery = new QueryParser(Constant.COMMENTTEXT_FIELD, analyzer).parse(query);
                finalQuery.add(luceneQuery, BooleanClause.Occur.MUST);
            }


            searcher.search(finalQuery.build(), collector);
            ScoreDoc[] hits = collector.topDocs().scoreDocs;

            LOGGER.log(Level.INFO, "Found " + hits.length + " hits.");
            for (ScoreDoc hit : hits) {
                Document document = searcher.doc(hit.doc);
                String commentId = document.get(Constant.COMMENTID_FIELD);
                Comment comment = commentRepository.findById(commentId).get();
                comments.add(comment);
            }

            reader.close();
        } catch(IOException | ParseException ex) {
            ex.printStackTrace();
        }
        return comments;
    }
}