//package gitlet;
//
//import java.io.File;
//import static gitlet.Utils.*;
//
//// TODO: any imports you need here
//
///** Represents a gitlet repository.
// *  TODO: It's a good idea to give a description here of what else this Class
// *  does at a high level.
// *
// *  @author TODO
// */
//public class Repository {
//    /**
//     * TODO: add instance variables here.
//     *
//     * List all instance variables of the Repository class here with a useful
//     * comment above them describing what that variable represents and how that
//     * variable is used. We've provided two examples for you.
//     */
//
//    /** The current working directory. */
//    public static final File CWD = new File(System.getProperty("user.dir"));
//    /** The .gitlet directory. */
//    public static final File GITLET_DIR = join(CWD, ".gitlet");
//
//    public static void init(){
//        if (!GITLET_DIR.exists()){
//            GITLET_DIR.mkdir();
//        }
//        else{
//            System.out.println("A Gitlet version-control system already exists in the current directory.");
//            }
//
//        Commit Commit_0 = new Commit();
//
//    }
//}

package gitlet;

//import javassist.Loader;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    public Commit currentCommit;

    public Staging staging;
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /**
     * The current working directory.
     */

    public Repository() throws IOException {
//        currentCommit = null;
//        staging = new Staging();
        //TA said need to initialize all the files/directories before making all the stuff
        //he said maybe do a helper method and change up logic for commits
    }

    public static final File CWD = new File(System.getProperty("user.dir"));
    /**
     * The .gitlet directory.
     */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    public static final File COMMITS_DIR = join(GITLET_DIR, ".commit");

    public static final File BLOBS_DIR = join(GITLET_DIR, ".blobs");

    public static final File HEAD = join(GITLET_DIR, ".head");

    public static final File STAGING_AREA = join(GITLET_DIR, ".staging");

    private static final File StagedFiles = Utils.join(GITLET_DIR, "StagedFiles");
    private static final File filesstagedTree = Utils.join(GITLET_DIR, "filesstagedTree");

    public static final File BRANCHES_DIR = Utils.join(GITLET_DIR, ".branches");

    public static final File activeBranch = Utils.join(GITLET_DIR, "activeBranch");

    public static final File filesstagedrmtree = Utils.join(GITLET_DIR, "filesstagedrmTree");


//The directory for the staged files


    /* TODO: fill in the rest of this class. */
    public void init() throws IOException {

        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            return;
        }
        GITLET_DIR.mkdir();
        COMMITS_DIR.mkdir();
        BLOBS_DIR.mkdir();
        BRANCHES_DIR.mkdir();
        staging = new Staging();
        this.setFilesstagedrmTree(new TreeMap<String,File>());
        this.setFilesstagedTree(new TreeMap<String,File>());
        File stagingObject = Utils.join(GITLET_DIR, "stagingObject");
        Utils.writeObject(stagingObject, staging);

        Commit newCommit = new Commit();



    }


    public static boolean restoreFile(String filename) {

        File HeadCommite = Utils.join(Repository.GITLET_DIR, "HeadCommit");
        Commit HEADCOMMI = Utils.readObject(HeadCommite, Commit.class);


        //Checking if the file version exists in the HEADCommit
        File CommitFiles = Utils.join(Repository.GITLET_DIR, ".treemap" + HEADCOMMI.hash);
        TreeMap<String, String> commitFiles = Utils.readObject(CommitFiles, TreeMap.class);
        if (!commitFiles.containsKey(filename)) {
            System.out.println("File does not exist in that commit.");
        }

        //Replaces the currentFile with the blob contents
        File currentFile = Utils.join(CWD, filename);
        File restored_file = Utils.join(BLOBS_DIR, filename + HEADCOMMI.hash);
        Blobs blob = Utils.readObject(restored_file, Blobs.class);
        String blobcontents = blob.getFilecontents();

        Utils.writeContents(currentFile, blobcontents);
        return true;

    }

    public void add(String fileName) {

        File filesstagedTree = Utils.join(GITLET_DIR, "filesstagedTree");
        TreeMap staging = Utils.readObject(filesstagedTree, TreeMap.class);
        File file = Utils.join(CWD, fileName);
        if (!file.exists()) {
            System.out.println("File does not exist.");
        } else {
            //check if file is already staged for addition
            //add copy of file to staging area
            if (staging.containsKey(file.getName())) {
                //if staging area contains the file
                File stagedFile = (File) staging.get(file.getName());
                if (isSameContent(file, stagedFile)) {
                    //if the content is the same as staged content, remove from staging area
                    staging.remove(file.getName());
                }
            }
            staging.put(file.getName(), file);
            Utils.writeObject(filesstagedTree, staging);

        }
    }

    private boolean checkifFileexistsinstaged(String filename) {
        File filesstagedTree = Utils.join(Repository.GITLET_DIR, "filesstagedTree");
        TreeMap treeacquired = Utils.readObject(filesstagedTree, TreeMap.class);
        for (Object file : treeacquired.keySet()) {
            if (filename.equals(file) == true) {
                return true;
            }
        }
        return false;
    }

    public void rm(String filename) {

        Commit Headcommit = getHead();

        //If the file is loaded onto staging area for addition
        File filesstagedTree = Utils.join(Repository.GITLET_DIR, "filesstagedTree");
        TreeMap staging = Utils.readObject(filesstagedTree, TreeMap.class);
        File file = Utils.join(CWD, filename);

        if (checkifFileexistsinstaged(filename) == true) {
            staging.remove(filename, staging.get(filename));
        }
        this.setFilesstagedTree(staging);

        //If the files is tracked in the current commit
        //Check if file is being "tracked"

        if (fileinHead(filename) == true) {
            TreeMap removaltrees = getremovalstagingtree();
            removaltrees.put(filename, file);
            this.setFilesstagedrmTree(removaltrees);
            file.delete();
        }
        else {
            System.out.println("No reason to remove the file.");
        }
    }

    public TreeMap getremovalstagingtree() {
        File removaltreef = Utils.join(GITLET_DIR, "filesstagedrmTree");
        TreeMap removaltree = Utils.readObject(removaltreef, TreeMap.class);
        return removaltree;
    }

    public void setFilesstagedrmTree(TreeMap removal){
        File staged = Utils.join(GITLET_DIR, "filesstagedrmTree");
        Utils.writeObject(staged,removal);
    }
    public void setFilesstagedTree(TreeMap additional){
        File staged = Utils.join(GITLET_DIR, "filesstagedTree");
        Utils.writeObject(staged,additional);
    }

    public boolean fileinHead(String filename) {
        Commit Headcommit = getHead();
        for (String filetrack : Headcommit.files_tracked) {
            if (filetrack.equals(filename)) {
                return true;
            }
        }
        return false;
    }

    public void global_log() {

        for (String fileName : Utils.plainFilenamesIn(COMMITS_DIR)) {
            File currentFile = Utils.join(COMMITS_DIR, fileName);
            Commit current = Utils.readObject(currentFile, Commit.class);
            System.out.println("===");
            System.out.println("commit " + current.hash);
            System.out.println("Date: " + current.timeStamp);
            System.out.println(current.message);
            System.out.println();
        }
    }

    public void status() {
        System.out.println("=== Branches ===");
        for(String filename: Utils.plainFilenamesIn(BRANCHES_DIR)){
            if(filename.equals(getactivepointer())){
                System.out.println("*"+filename);
            } else {
            System.out.println(filename);}
        }

        System.out.println();

        File filesstagedfile = Utils.join(GITLET_DIR, "filesstagedTree");
        TreeMap<String,File> filesstagedTree = Utils.readObject(filesstagedfile,TreeMap.class);
        System.out.println("=== Staged Files ===");
        for (String fileadded: filesstagedTree.keySet()){
            System.out.println(fileadded);
        }


        System.out.println();

        File filesstagedfilerm = Utils.join(GITLET_DIR, "filesstagedrmTree");
        TreeMap<String,File> filesstagedrmTree = Utils.readObject(filesstagedfilerm,TreeMap.class);
        System.out.println("=== Removed Files ===");
        for (String fileadded: filesstagedrmTree.keySet()){
            System.out.println(fileadded);
        }
        System.out.println();
        System.out.println("=== Modifications Not Staged For Commit ===");
        System.out.println();
        System.out.println("=== Untracked Files ===");

    }

    public Commit getBranch(String branchname){
        File branchfile = Utils.join(BRANCHES_DIR,branchname);
        Commit branch = Utils.readObject(branchfile,Commit.class);
        return branch;
    }

    public void branch(String branchname) {
        for (String branchnam: Utils.plainFilenamesIn(BRANCHES_DIR)){
            if (branchnam.equals(branchname)){
                System.out.println("A branch with that name already exists.");
                return;
            }
        }
        Commit HEADCOMMIT = getHead();
        Commit newCommit = HEADCOMMIT;
        File newBranch = Utils.join(BRANCHES_DIR, branchname);
        Utils.writeObject(newBranch, newCommit);

    }

    public void switchBranch(String branchname) {
        Commit initialac = Utils.readObject(Utils.join(Repository.BRANCHES_DIR,getactivepointer()), Commit.class);

        if (isFileintheFile(BRANCHES_DIR,branchname) == false){
            System.out.println("No such branch exists.");
            return;
        }

        File branchfile = Utils.join(BRANCHES_DIR, branchname);
        Commit branch = Utils.readObject(branchfile, Commit.class);

        //Takes all files in the commit at the head of the given branch and puts them in the working directory
        for (String filename: branch.files_tracked){

            //Retreives the contents of the blob of the particular commit and file in that commit
            File blobfile = Utils.join(BLOBS_DIR, filename+branch.hash);
            Blobs blobs = Utils.readObject(blobfile,Blobs.class);
            String blobcontents = blobs.getFilecontents();

            //writes the contents of the blob into the new file in CWD
            File movedfile = Utils.join(CWD, filename);
            Utils.writeContents(movedfile,blobcontents); //Does this write the contents?
        }


        Commit oldpointer = Utils.readObject(Utils.join(BRANCHES_DIR,getactivepointer()),Commit.class);
        //Get files in the current active branch and delete the ones that are not in this branch
        for (String fileinold: oldpointer.files_tracked){
            if (isFileintheCommit(branch,fileinold) == false){
                File file = Utils.join(CWD, fileinold);
                Commit main = Utils.readObject(Utils.join(BRANCHES_DIR,"main"), Commit.class);
                file.delete();
                Commit main2 = Utils.readObject(Utils.join(BRANCHES_DIR,"main"), Commit.class);
            }



        }







        //updating the active branch
        String activebranch = branchname;
        File newAB = Utils.join(Repository.GITLET_DIR, "activeBranch");
        Utils.writeObject(newAB, activebranch);
        //updated the active branch to the provided branch
//changed iinto string

        //updating the HEAD pointer
        File HEADCoMM = Utils.join(Repository.GITLET_DIR, "HeadCommit");
        Utils.writeObject(HEADCoMM, Utils.readObject(Utils.join(Repository.BRANCHES_DIR,activebranch), Commit.class));

        //the staging area is cleared
        File filesstagedTree = Utils.join(Repository.GITLET_DIR, "filesstagedTree");
        TreeMap staging = Utils.readObject(filesstagedTree, TreeMap.class);
        staging.clear();
        Utils.writeObject(filesstagedTree,staging);

        Commit initialac2 = Utils.readObject(Utils.join(Repository.BRANCHES_DIR,getactivepointer()), Commit.class);
        String Lebron = "James";

    }

    public boolean isFileintheCommit (Commit commit, String filename){
        for (String filenamesincom: commit.files_tracked){
            if (filename.equals(filenamesincom)){
                return true;
            }
        }
        return false;
    }

    public boolean isFileintheFile(File file, String filename){ //Checks if the filename is in the given file
        for (String filei: Utils.plainFilenamesIn(file)){
            if (filei.equals(filename)){
                return true;
            }
        }
        return false;
    }

    public void remove_branch(String branchname) { //Check if this is correct

        String currentBranch = getactivepointer();
        File branchfile = Utils.join(BRANCHES_DIR, branchname);
        Commit branch = Utils.readObject(branchfile,Commit.class);
        //Removes the files associated with the branch.
        for (String file_in_the_branch:Utils.plainFilenamesIn(BRANCHES_DIR)){
            File file = Utils.join(CWD,file_in_the_branch);
            file.delete();
        }
        if (!branchfile.exists()) {
            System.out.println("A branch with that name does not exist.");
        }
        if (branchname.equals(currentBranch)) {
            System.out.println("Cannot remove the current branch.");
        }
        branchfile.delete();
    }

    public void reset(String commitID) {

        //Approach: We are going to go through every file in the filesstagedTree and then you can call restore on those files
        File currentCommitFile = Utils.join(COMMITS_DIR, commitID);
        Commit currentCommit = Utils.readObject(currentCommitFile, Commit.class); //reading the ccommit and setting it to currentCommit
        for (String filename : currentCommit.getCommit_tracker().keySet()) { //getting all the filenames from the currentCommit
            this.restoreFile(filename, commitID); //restoring all the files in the commit
        }

        File HEADCommit = Utils.join(GITLET_DIR, "HeadCommit");
        Utils.writeContents(HEADCommit, currentCommit);

    }

    public Commit getHead() {
        File Heacommi = Utils.join(GITLET_DIR, "HeadCommit");
        Commit heac = Utils.readObject(Heacommi, Commit.class);
        return heac;
    }

    public String getactivepointer() {
        File apf = Utils.join(GITLET_DIR, "activeBranch");
        String ap = Utils.readObject(apf, String.class);
        return ap;
    }


    private boolean isSameContent(File file1, File file2) {
        if (readContents(file1) == readContents(file2)) {
            return true;
        } else {
            return false;
        }
    }

    public void commit(String message) {

        File filesstagedtree = Utils.join(GITLET_DIR, "filesstagedTree");
        File filesstagedrmtree = Utils.join(GITLET_DIR,"filesstagedrmTree");
        TreeMap<String,File> stagingforadd = Utils.readObject(filesstagedtree, TreeMap.class);
        TreeMap<String,File> stagingforremoval = Utils.readObject(filesstagedrmtree, TreeMap.class);
        if (stagingforadd.isEmpty() && stagingforremoval.isEmpty()) {
            System.out.println("No changes added to the commit.");
            return;
        }
        if (message.isEmpty() || message.isBlank()) {
            System.out.println("Please enter a commit message.");
            return;

        } if (stagingforadd.isEmpty()==false) {
            Commit newCommit = new Commit(message);
            stagingforadd.clear();
            setFilesstagedTree(stagingforadd);
        }
        if (stagingforremoval.isEmpty() == false){
            File pointerf = Utils.join(BRANCHES_DIR,getactivepointer());
            Commit pointer= Utils.readObject(pointerf,Commit.class);
            for (String filen: stagingforremoval.keySet()){
                pointer.files_tracked.remove(filen);
            }
            stagingforremoval.clear();
            setFilesstagedrmTree(stagingforremoval);
        }
    }

    public void log() {

        Commit current = Utils.readObject(Utils.join(BRANCHES_DIR,getactivepointer()),Commit.class);
        while (current != null) {
            System.out.println("===");
            System.out.println("commit " + current.hash);
            System.out.println("Date: " + current.timeStamp);
            System.out.println(current.message);
            System.out.println();
            current = current.getParentCommitnotS();

        }
    }


    public boolean restore() {
        return true;
    }

    public boolean commitidinlist(String iD) {

        for (int i = 0; i < Repository.GITLET_DIR.list().length; i++) {
            if (iD == Repository.GITLET_DIR.list()[i]) {
                return true;
            }
        }
        return false;
    }


    public boolean restoreFile(String filename, String CommitID) {


        File Commite = Utils.join(Repository.COMMITS_DIR, CommitID);
        Commit COMMI = Utils.readObject(Commite, Commit.class);


        //Checking if the file version exists in the HEADCommit
        File CommitFiles = Utils.join(Repository.GITLET_DIR, ".treemap" + COMMI.hash);
        TreeMap<String, String> commitFiles = Utils.readObject(CommitFiles, TreeMap.class);
        if (commitFiles.containsKey(filename) == false) {
            System.out.println("File does not exist in that commit.");
        }

        //Replaces the currentFile with the blob contents
        File currentFile = Utils.join(CWD, filename);
        File restored_file = Utils.join(BLOBS_DIR, filename + COMMI.hash);
        Blobs blob = Utils.readObject(restored_file, Blobs.class);
        String blobcontents = blob.getFilecontents();

        Utils.writeContents(currentFile, blobcontents);
        ;
        return true;

    }


    public void merge(String branchName) {
        boolean mergeConflict = false;
        ArrayList<File> fileList;
        if (mergeConlict1(branchName)) {
            return;
        }
    }

    private boolean mergeConlict1(String branchName) {
        String currentBranchName = getactivepointer();
        File branchfile = Utils.join(BRANCHES_DIR, branchName);

        if (!staging.stagedFiles.isEmpty() || !staging.removedFiles.isEmpty()) {
            System.out.println("You have uncommitted changes.");
            return true;
        }
        else if (!branchfile.exists()){
            System.out.println("A branch with that name does not exist.");
            return true;
        }
        else if (branchName.equals(currentBranchName)) {
            System.out.println("Cannot merge a branch with iteslef.");
            return true;
        }
        return false;
    }


}
