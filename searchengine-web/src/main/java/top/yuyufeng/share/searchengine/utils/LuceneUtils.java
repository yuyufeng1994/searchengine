package top.yuyufeng.share.searchengine.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.util.StringUtils;
import org.wltea.analyzer.lucene.IKAnalyzer;
import top.yuyufeng.share.searchengine.model.StationTo;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author yuyufeng
 * @date 2018/7/3.
 */
public class LuceneUtils {
    private static String path = "c://test/lucene_web";

    public static void setPath(String path) {
        LuceneUtils.path = path;
    }

    private static Analyzer analyzer = new IKAnalyzer();

    public static void main(String[] args) {
       /* StationTo model = new StationTo();
        model.setStartAreaId("330482");
        model.setStartAreaName("杭州");
        model.setStartAreaNameAbbr("hz");
        model.setStartAreaNamePinYin("hangzhou");
        model.setZoneId("1001");
        model.setZoneName("平湖");
        model.setZoneNameAbbr("ph");
        model.setZoneNamePinYin("pinghu");
        doIndex(model);
        System.out.println(model);*/

        PageInfo<StationTo> pageInfo = doSearch("hz", 1);
        if (pageInfo.getList() != null) {
            for (StationTo stationTo : pageInfo.getList()) {
                System.out.println(stationTo);
            }
        }


    }

    public synchronized static StationTo doIndex(StationTo model) {
        // 实例化IKAnalyzer分词器
        Directory directory = null;
        IndexWriter iwriter;
        try {
            // 索引目录
            directory = new SimpleFSDirectory(new File(path));

            // 配置IndexWriterConfig
            IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LUCENE_47, analyzer);
            iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            iwriter = new IndexWriter(directory, iwConfig);
            // 写入索引
            Document doc = new Document();
            String id = model.getId();
            if (StringUtils.isEmpty(id)) {
                id = UUID.randomUUID().toString();
            }
            doc.add(new StringField("ID", id, Field.Store.YES));
            doc.add(new StringField("STARTAREAID", model.getStartAreaId(), Field.Store.YES));
            doc.add(new TextField("STARTAREANAME", model.getStartAreaName(), Field.Store.YES));
            doc.add(new TextField("STARTAREANAMEABBR", model.getStartAreaNameAbbr(), Field.Store.YES));
            doc.add(new TextField("STARTAREANAMEPINYIN", model.getStartAreaNamePinYin(), Field.Store.YES));
            doc.add(new StringField("ZONEID", model.getZoneId(), Field.Store.YES));
            doc.add(new TextField("ZONENAME", model.getZoneName(), Field.Store.YES));
            doc.add(new TextField("ZONENAMEABBR", model.getZoneNameAbbr(), Field.Store.YES));
            doc.add(new TextField("ZONENAMEPINYIN", model.getZoneNamePinYin(), Field.Store.YES));
            doc.add(new TextField("KEYWORDSGROUP", model.getKeywordsGroup(), Field.Store.YES));

            if (StringUtils.isEmpty(model.getId())) {
                iwriter.addDocument(doc);
            } else {
                iwriter.updateDocument(new Term("ID", id), doc);
            }
            iwriter.close();
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (LockObtainFailedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (directory != null) {
                try {
                    directory.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return model;
    }

    public static PageInfo<StationTo> doSearch(String keyword, int page) {
        int size = 10;
        Page<StationTo> pageIn = new Page<>(page, size);
        Directory directory = null;
        IndexReader ireader = null;
        IndexSearcher isearcher;
        int total = 0;
        try {
            //索引目录
            directory = new SimpleFSDirectory(new File(path));
            // 配置IndexWriterConfig
            IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LUCENE_47, analyzer);
            iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

            ireader = DirectoryReader.open(directory);
            isearcher = new IndexSearcher(ireader);


            QueryParser qp = new QueryParser(Version.LUCENE_47, "KEYWORDSGROUP", analyzer);
            qp.setDefaultOperator(QueryParser.OR_OPERATOR);  // and or 跟数据库查询语法类似
            Query query = qp.parse(keyword);
            qp.setDefaultOperator(QueryParser.OR_OPERATOR);  // and or 跟数据库查询语法类似


            TopDocs topDocs = isearcher.search(query, 1000);
            total = topDocs.totalHits;

            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            Highlighter highlighter = new Highlighter(new SimpleHTMLFormatter("<font style='color:red'>", "</font>"),
                    new QueryScorer(query));
            Fragmenter fragmenter = new SimpleFragmenter(100);
            highlighter.setTextFragmenter(fragmenter);

            for (int i = (page - 1) * size; i < ((page - 1) * size + size > total ? total : (page - 1) * size + size); i++) {
                Document targetDoc = isearcher.doc(scoreDocs[i].doc);
                String startAreaName = targetDoc.get("STARTAREANAME");
                String startAreaNameAbbr = targetDoc.get("STARTAREANAMEABBR");
                String startAreaNamePinYin = targetDoc.get("STARTAREANAMEPINYIN");
                String zoneName = targetDoc.get("ZONENAME");
                String zoneNameAbbr = targetDoc.get("ZONENAMEABBR");
                String zoneNamePinYin = targetDoc.get("ZONENAMEPINYIN");

                String startAreaNameHl = highlighter.getBestFragment(analyzer, "STARTAREANAME", startAreaName);
                startAreaName = StringUtils.isEmpty(startAreaNameHl) ? startAreaName : startAreaNameHl;
                String startAreaNameAbbrHl = highlighter.getBestFragment(analyzer, "STARTAREANAMEABBR", startAreaNameAbbr);
                startAreaNameAbbr = StringUtils.isEmpty(startAreaNameAbbrHl) ? startAreaNameAbbr : startAreaNameAbbrHl;
                String startAreaNamePinYinHl = highlighter.getBestFragment(analyzer, "STARTAREANAMEPINYIN", startAreaNamePinYin);
                startAreaNamePinYin = StringUtils.isEmpty(startAreaNamePinYinHl) ? startAreaNamePinYin : startAreaNamePinYinHl;
                String zoneNameHl = highlighter.getBestFragment(analyzer, "ZONENAME", zoneName);
                zoneName = StringUtils.isEmpty(zoneNameHl) ? zoneName : zoneNameHl;
                String zoneNameAbbrHl = highlighter.getBestFragment(analyzer, "ZONENAMEABBR", zoneNameAbbr);
                zoneNameAbbr = StringUtils.isEmpty(zoneNameAbbrHl) ? zoneNameAbbr : zoneNameAbbrHl;
                String zoneNamePinYinHl = highlighter.getBestFragment(analyzer, "ZONENAMEPINYIN", zoneNamePinYin);
                zoneNamePinYin = StringUtils.isEmpty(zoneNamePinYinHl) ? zoneNamePinYin : zoneNamePinYinHl;

                StationTo modelItem = new StationTo();
                modelItem.setId(targetDoc.get("ID"));
                modelItem.setStartAreaId(targetDoc.get("STARTAREAID"));
                modelItem.setStartAreaName(startAreaName);
                modelItem.setStartAreaNameAbbr(startAreaNameAbbr);
                modelItem.setStartAreaNamePinYin(startAreaNamePinYin);
                modelItem.setZoneId(targetDoc.get("ZONEID"));
                modelItem.setZoneName(zoneName);
                modelItem.setZoneNameAbbr(zoneNameAbbr);
                modelItem.setZoneNamePinYin(zoneNamePinYin);

                pageIn.add(modelItem);

            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidTokenOffsetsException e) {
            e.printStackTrace();
        } finally {
            if (ireader != null) {
                try {
                    ireader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (directory != null) {
                try {
                    directory.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        pageIn.setTotal(total);
        PageInfo<StationTo> pageInfo = new PageInfo(pageIn);
        return pageInfo;
    }

    public static StationTo searchOne(StationTo hDoc) {
        StationTo model = new StationTo();
        Directory directory = null;
        IndexReader ireader = null;
        IndexSearcher isearcher;
        try {

            directory = new SimpleFSDirectory(new File(path));

            IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LUCENE_47, analyzer);
            iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

            ireader = DirectoryReader.open(directory);
            isearcher = new IndexSearcher(ireader);

            String keyword = hDoc.getId();
            Query query = new TermQuery(new Term("ID", keyword));

            TopDocs topDocs = isearcher.search(query, 1);

            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (int i = 0; i < topDocs.totalHits; i++) {
                Document targetDoc = isearcher.doc(scoreDocs[i].doc);
                model.setId(targetDoc.get("ID"));
                model.setStartAreaId(targetDoc.get("STARTAREAID"));
                model.setStartAreaName(targetDoc.get("STARTAREANAME"));
                model.setStartAreaNameAbbr(targetDoc.get("STARTAREANAMEABBR"));
                model.setStartAreaNamePinYin(targetDoc.get("STARTAREANAMEPINYIN"));
                model.setZoneId(targetDoc.get("ZONEID"));
                model.setZoneName(targetDoc.get("ZONENAME"));
                model.setZoneNameAbbr(targetDoc.get("ZONENAMEABBR"));
                model.setZoneNamePinYin(targetDoc.get("ZONENAMEPINYIN"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ireader != null) {
                try {
                    ireader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (directory != null) {
                try {
                    directory.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return model;
    }

    public static Boolean doDelete(String id) {
        // 实例化IKAnalyzer分词器
        Directory directory = null;
        IndexWriter iwriter;
        try {
            directory = new SimpleFSDirectory(new File(path));
            IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LUCENE_47, analyzer);
            iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            iwriter = new IndexWriter(directory, iwConfig);
            iwriter.deleteDocuments(new Term("ID", id));
            iwriter.close();
        } catch (CorruptIndexException e) {
            e.printStackTrace();
            return false;
        } catch (LockObtainFailedException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (directory != null) {
                try {
                    directory.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
