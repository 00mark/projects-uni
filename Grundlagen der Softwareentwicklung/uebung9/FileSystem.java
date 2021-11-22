package io;

import java.io.File;
import java.util.Arrays;

/**
 * Represents the file system beginning with a root element. The root element
 * may be a file or a directory. A FileSystem my be visited by a
 * {@link FileVisitor} by using the {@link #accept(FileVisitor)} method.
 * 
 * @author Mathias Meninghaus (mathias.menninghaus@uos.de)
 * 
 * @see FileVisitor
 * @see FileVisitResult
 * 
 */
public class FileSystem {

   private final File rootPath;

   /**
    * Instantiate a file system with a given root path.
    * 
    * @param rootPath
    *           the root element
    * 
    * @throws IllegalArgumentException
    *            if <code>rootPath</code> does not exist
    */
   public FileSystem(File rootPath) {
      if (!rootPath.exists()) {
         throw new IllegalArgumentException(rootPath + " does not exist");
      }
      this.rootPath = rootPath;
   }

   /**
    * Shows the given <code>FileVisitor</code> every element in this FileSystem.
    * Thus recursively goes through this FileSystem and calls
    * {@link FileVisitor#visitFile(File)} for every file in every directory of
    * this FileSystem. Also calls {@link FileVisitor#preVisitDirectory(File)}
    * before entering a directory and
    * {@link FileVisitor#postVisitDirectory(File)} after leaving a fully read
    * directory, i.e. after every element inside a directory has been visited.
    * Will stop visiting elements in this FileSystem if
    * {@link FileVisitResult#TERMINATE} is returned by the FileVisitor. Will not
    * visit the elements inside a directory, if
    * {@link FileVisitResult#SKIP_SUBTREE} is returned by the FileVisitors
    * {@link FileVisitor#preVisitDirectory(File)} method.
    * 
    * @param v
    *           the Visitor that shall see all elements in this FileSystem.
    */
   public void accept(FileVisitor v) {
      if (rootPath.isDirectory()) {
         for (File f : rootPath.listFiles()) {
            walkFile(f, v);
         }
      } else {
         v.visitFile(rootPath);
      }
   }
   
   private FileVisitResult walkFile(File currentFile, FileVisitor v) {

      if (!currentFile.canRead()) {
         return v.visitFailed(currentFile);
      }

      if (currentFile.isDirectory()) {
         FileVisitResult response = v.preVisitDirectory(currentFile);

         switch (response) {
         case CONTINUE:

            /*
             * sort - system dependent result
             */
            File[] files = currentFile.listFiles();
            Arrays.sort(files);

            for (File f : files) {
               if (walkFile(f, v) == FileVisitResult.TERMINATE) {
                  return FileVisitResult.TERMINATE;
               }
            }
            response = v.postVisitDirectory(currentFile);

         case SKIP_SUBTREE:
         case TERMINATE:
         default:
            return response;
         }
      } else {
         return v.visitFile(currentFile);

      }
   }
}
