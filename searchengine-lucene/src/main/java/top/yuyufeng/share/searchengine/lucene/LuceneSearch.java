package top.yuyufeng.share.searchengine.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;


/**
 * @author yuyufeng
 * @date 2018/9/14.
 */
public class LuceneSearch {
    public static void main(String[] args) {
        String keyword = "周杰伦和林书豪打篮球";
        // Lucene Document的域名
        String fieldName = "content";
        Analyzer analyzer = new IKAnalyzer(true);
        Directory directory = null;
        IndexReader ireader = null;
        IndexSearcher isearcher;

        try {
            //索引目录
            directory = new SimpleFSDirectory(new File("c://test/lucene_test"));
            // 配置IndexWriterConfig
            IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LUCENE_47, analyzer);
            iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

            // 搜索过程**********************************
            // 实例化搜索器
            ireader = DirectoryReader.open(directory);
            isearcher = new IndexSearcher(ireader);
            // 使用QueryParser查询分析器构造Query对象
            QueryParser qp = new QueryParser(Version.LUCENE_47, fieldName, analyzer);
            qp.setDefaultOperator(QueryParser.OR_OPERATOR);  // and or 跟数据库查询语法类似
            Query query = qp.parse(keyword);
            System.out.println("Query = " + query);

            // 搜索相似度最高的5条记录
            TopDocs topDocs = isearcher.search(query, 10);
            System.out.println("命中：" + topDocs.totalHits);
            // 遍历输出结果
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (int i = 0; i < topDocs.totalHits; i++) {
                Document targetDoc = isearcher.doc(scoreDocs[i].doc);
                System.out.println("内容：" + targetDoc.toString());
            }
        } catch (ParseException e) {
            e.printStackTrace();
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

    }
}
