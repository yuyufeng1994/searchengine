package top.yuyufeng.share.searchengine.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * @author yuyufeng
 * @date 2018/9/14.
 */
public class LuceneIndex {
    public static void main(String[] args) {
        doIndex("A", "content", "周杰伦是一个歌手，也是一个演员。");
        doIndex("B", "content", "周杰伦出生于台湾省。");
        doIndex("C", "content", "林书豪是一个NBA篮球运动员，效力于亚特兰大老鹰队。");
        doIndex("D", "content", "林书豪很喜欢周杰伦创作的歌曲。");
        doIndex("E", "content", " 林书豪和周杰伦担任近期的《这就是灌篮》篮球综艺节目。");
    }

    private static void doIndex(String id, String fieldName, String text) {
        // 实例化IKAnalyzer分词器
        Analyzer analyzer = new IKAnalyzer(true);

        Directory directory = null;
        IndexWriter iwriter;
        try {
            // 索引目录
            directory = new SimpleFSDirectory(new File("c://test/lucene_test"));

            // 配置IndexWriterConfig
            IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LUCENE_47, analyzer);
            iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            iwriter = new IndexWriter(directory, iwConfig);
            // 写入索引
            Document doc = new Document();
            doc.add(new StringField("ID", id, Field.Store.YES));
            doc.add(new TextField(fieldName, text, Field.Store.YES));
            iwriter.addDocument(doc);
            iwriter.close();
            System.out.println("建立索引成功:" + id);
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
    }
}
