package io;

import java.io.File;

/**
 * A visitor to visit FileSystem instances. May be used in
 * {@link FileSystem#accept(FileVisitor)}.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 * @see FileSystem
 * @see FileVisitResult
 * 
 */
public interface FileVisitor {

   /**
    * Called by the visited FileSystem after a directory has been read, i.e.
    * after every file and sub-directory in <code>dir</code> has been visited by
    * this FileVisitor. May return {@link FileVisitResult#CONTINUE} if the
    * visiting of files and directories should continue or
    * {@link FileVisitResult#TERMINATE} if not.
    * {@link FileVisitResult#SKIP_SUBTREE} will have the same effect as
    * {@link FileVisitResult#CONTINUE}.
    * 
    * @param dir
    *           the directory that has been visited
    * @return if the visit should continue or not.
    */
   public FileVisitResult postVisitDirectory(File dir);

   /**
    * 
    * Called by the visiting FileSystem before a directory will recursively be
    * read. May return {@link FileVisitResult#CONTINUE} if the directory should
    * be read or {@link FileVisitResult#SKIP_SUBTREE} if the given directory
    * should not be read but the visit of the FileSystem should continue in
    * general. Should return {@link FileVisitResult#TERMINATE} if the visit of
    * the FileSystem in general should end.
    * 
    * @param dir
    *           the directory that may be visited
    * @return if the visit should continue, skip the given directory or end at
    *         all.
    */
   public FileVisitResult preVisitDirectory(File dir);

   /**
    * Called by the visiting FileSystem if the visitation of a directory failed,
    * thus if the directory is not-readable. May return
    * {@link FileVisitResult#CONTINUE} if the visiting of files and directories
    * should continue or {@link FileVisitResult#TERMINATE} if not.
    * {@link FileVisitResult#SKIP_SUBTREE} will have the same effect as
    * {@link FileVisitResult#CONTINUE}.
    * 
    * @param dir
    *           the directory that could not be visited
    * @return if the visit should continue or not.
    */
   public FileVisitResult visitFailed(File dir);

   /**
    * Called by the visiting FileSystem to visit a file with this FileVisitor.
    * May return {@link FileVisitResult#CONTINUE} if the visiting of files and
    * directories should continue or {@link FileVisitResult#TERMINATE} if not.
    * {@link FileVisitResult#SKIP_SUBTREE} will have the same effect as
    * {@link FileVisitResult#CONTINUE}.
    * 
    * @param file
    *           the file that is visited
    * @return if the visit should continue or not.
    */
   public FileVisitResult visitFile(File file);

}
