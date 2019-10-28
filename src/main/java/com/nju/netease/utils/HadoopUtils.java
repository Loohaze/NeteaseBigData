package com.nju.netease.utils;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.List;

public class HadoopUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HadoopUtils.class);
    private static Configuration conf = new Configuration();
    private static FileSystem hdfs;
    private static String dir;

    private static String HDFSURI="hdfs://172.19.240.124:9000/";

    public HadoopUtils(String hadoopDir,String hdfsURI) {
        if (hdfsURI==null || hdfsURI.equals("")){
            this.HDFSURI="hdfs://172.19.240.124:9000/";
        }else{
            this.HDFSURI=hdfsURI;
        }

        UserGroupInformation userGroupInformation = UserGroupInformation.createRemoteUser("root");
        try {
            userGroupInformation.doAs((PrivilegedExceptionAction<Void>) () -> {
                Configuration conf = new Configuration();
                conf.set("fs.defaultFS", HDFSURI);
                conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
                conf.set("dfs.replication", "2");
                Path path = new Path(HDFSURI);
                hdfs = FileSystem.get(path.toUri(), conf);
                dir = hadoopDir;
                return null;
            });
            this.deleteFileDir(dir);
            this.createDir();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * CREATE DIR ON HADOOP WHOSE NAME IS CLAIMED AS GLOBAL VARIABLE
     */
    public void createDir() throws IOException {
        Path path = new Path(dir);
        if (hdfs.exists(path)) {
            LOGGER.error("[ERROR]===(EXISTED DIR)CREATE DIR ON HADOOP:[" + conf.get("fs.defaultFS") + dir + "] ,HOWEVER EXISTS");
        } else {
            hdfs.mkdirs(path);
            LOGGER.info("[SUCCESS]===SUCCESS CREATE DIR ON HADOOP:[" + conf.get("fs.defaultFS") + dir+"]");
        }
    }

    /**
     * COPY LOCAL FILE TO HADOOP DIR
     *
     * NOTE : PARAM IS LOCAL-FILE NOT LOCAL-DIR
     * @param localSrcFile LOCAL FILE AB-PATH
     */
    public void copyLocalFileToHadoop(String localSrcFile) throws IOException {
        Path src = new Path(localSrcFile);
        Path dst = new Path(dir);
        if (!(new File(localSrcFile)).exists()) {
            LOGGER.error("[ERROR]===COPY FROM LOCAL :[" + localSrcFile
                    + "] TO HADOOP FAIL FOR NO EXISTS");
        } else if (!hdfs.exists(dst)) {
            LOGGER.error("[ERROR]===NO HADOOP DIR:[" + dst.toUri()
                    + "] FOR COPY FROM LOCAL");
        } else {
            String dstPath = dst.toUri() + "/" + src.getName();
            if (hdfs.exists(new Path(dstPath))) {
                LOGGER.warn("[WARNING]===HADOOP DIR :[" + dstPath
                        + "] HAS BEEN OVER WRITTEN");
            }
            hdfs.copyFromLocalFile(src, dst);
            LOGGER.info("[SUCCESS]===COPY LOCAL FILE:["+localSrcFile+"] TO HADOOP : [" + conf.get("fs.defaultFS")
                    + dir+"] SUCCESS");
        }
    }

    /**
     * CREATE A NEW FILE ON HADOOP OR OVER WRITE IF EXISTS
     *
     * @param fileName    TARGET FILE NAME,EXISTS BELOW DIR
     * @param fileContent CONTENT TO WRITE
     */
    public void createHadoopFile(String fileName, String fileContent) throws IOException {
        Path dst = new Path(dir + fileName);
        FSDataOutputStream output = hdfs.create(dst);
        output.write(fileContent.getBytes());
        output.close();
        LOGGER.info("[SUCCESS]===CREATE NEW FILE ON HADOOP :[" + conf.get("fs.defaultFS")
                + fileName+"]");
    }

    /**
     * APPEND WRITE CONTENT TO FILE EXISTS ON HADOOP
     *
     * @param fileName    FILE ON HADOOP
     * @param fileContent CONTENT TO BE APPEND
     */
    public void appendFile(String fileName, String fileContent) throws IOException {
        Path dst = new Path(dir + fileName);
        byte[] bytes = fileContent.getBytes();
        if (!hdfs.exists(dst)) {
            LOGGER.warn("[WARNING]===NONE EXISTS FILE:["+dst+"] ON HADOOP , AND WILL CREATE!");
            createHadoopFile(fileName, fileContent);
        }else{
            FSDataOutputStream output = hdfs.append(dst);
            output.write(bytes);
            LOGGER.info("[SUCCESS]===SUCCESS APPEND NEW CONTENT TO HADOOP FILE:[" + conf.get("fs.defaultFS")
                    + fileName+"]");
            output.flush();
            output.close();
        }

    }

    /**
     * GET FILE NAMES OF HADOOP DIR
     * @param dirName DIR NAME OF HADOOP-DIR
     */
    public List<String> getAllFileName(String dirName) throws IOException {
        Path f = new Path(dirName);
        FileStatus[] status = hdfs.listStatus(f);

        List<String> allFiles=new ArrayList<>();
        for (FileStatus fileStatus : status) {
            allFiles.add(fileStatus.getPath().toString());
            System.out.println(fileStatus.getPath().toString());
        }
        return allFiles;
    }

    /**
     * DELETE FILE ON HADOOP
     *
     * @param fileName FILE NAME WHICH NEED TO BE DELETED
     */
    public void deleteFile(String fileName) throws IOException {
        Path f = new Path(dir + fileName);
        boolean isExists = hdfs.exists(f);
        if (isExists) {
            boolean isDel = hdfs.delete(f, true);
            LOGGER.info(fileName + "[INFO]===DELETE FILE :["+fileName+"] " + (isDel ? "SUCCESS" : "FAIL"));
        } else {
            LOGGER.info("[ERROR]===FILE NEED TO BE DELETED :["+fileName + "] NONE EXISTED ");
        }
    }

    /**
     * DELETE DIR ON HADOOP
     *
     * @param fileDir DIR NAME
     */
    public void deleteFileDir(String fileDir) throws IOException {
        Path f = new Path(fileDir);
        boolean isExists = hdfs.exists(f);
        if (isExists) {
            boolean isDel = hdfs.delete(f, true);
            LOGGER.info("[INFO]===DELETE DIR :["+fileDir + "] " + (isDel ? "SUCCESS" : "FAIL"));
        } else {
            LOGGER.error("[ERROR]===DIR NEED TO BE DELETED :[ " + fileDir + "] NONE EXISTS");
        }
    }


//    public static void main(String[] args) throws IOException, InterruptedException {
//        HadoopUtils hadoopUtils=new HadoopUtils("/testHadoop/","hdfs://172.19.240.124:9000/");
////        hadoopUtils.transferLocalDirToHDFS("C:\\Users\\MR_CAI\\AppData\\Roaming\\CppMonitor\\Dao1\\","/testHadoop/","E:\\testHDFS\\");
////        hadoopUtils.copyLocalFileToHadoop("C:\\Users\\MR_CAI\\AppData\\Roaming\\CppMonitor\\Dao1\\Integrate\\integrate4.json");
////        int i=1;
////        while(true){
////            int index=i%5;
////            System.err.println(index);
////            hadoopUtils.copyLocalFileToHadoop("C:\\Users\\MR_CAI\\AppData\\Roaming\\CppMonitor\\Dao1\\Integrate\\integrate"+index+".json");
////            i++;
////            Thread.sleep(3000);
////        }
//        hadoopUtils.copyLocalFileToHadoop("D:\\qq文件\\731744067\\FileRecv\\popular.txt");
////        hadoopUtils.copyLocalFileToHadoop("D:\\qq文件\\731744067\\FileRecv\\list1.txt");
//    }
}
