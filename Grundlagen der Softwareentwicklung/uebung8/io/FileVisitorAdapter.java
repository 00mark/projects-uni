package io;

import java.io.File;

/**
 * Adapter class for the FileVistor. Empty implementation of every method. Every
 * method will return {@link FileVisitResult#CONTINUE}.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class FileVisitorAdapter implements FileVisitor {

   @Override
   public FileVisitResult postVisitDirectory(File dir) {
      return FileVisitResult.CONTINUE;
   }

   @Override
   public FileVisitResult preVisitDirectory(File dir) {
      return FileVisitResult.CONTINUE;
   }

   @Override
   public FileVisitResult visitFailed(File file) {
      return FileVisitResult.CONTINUE;
   }

   @Override
   public FileVisitResult visitFile(File file) {
      return FileVisitResult.CONTINUE;
   }

}
