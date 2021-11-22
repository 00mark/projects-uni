package io;

/**
 * Return values for a {@link FileVisitor}
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 * 
 * @see FileVisitor
 * @see FileSystem
 */
public enum FileVisitResult {
   /**
    * continues the visit of a File System
    */
   CONTINUE,
   /**
    * skips the visitation of directory, only has an effect when returned by
    * {@link FileVisitor#preVisitDirectory(io.File)}
    */
   SKIP_SUBTREE,
   /**
    * the visitation of the file system will immediately be stopped
    */
   TERMINATE;

}
