
package gitlet;
import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;

import static gitlet.Utils.join;

// TODO: any imports you need here

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /**
     * The message of this Commit.
     */
    public String hash;
    public String message;
    public String timeStamp;
    public String nextCommit;
    public Commit HEADCommit;

    public String parentCommit;

    public ArrayList<String> files_added_for_a_branch = new ArrayList<>();

    public ArrayList<String> files_tracked = new ArrayList<String>(); //Keeps track of the filename in the commit






    private static final SimpleDateFormat timeDate = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z");

    private TreeMap<String, String> commit_tracker = new TreeMap<String, String>(); //Here, using a treeMap we can attach an blob SHAI and hand it to a file name




    public TreeMap<String, String> getCommit_tracker() {
        return commit_tracker;
    }

    public List<Commit> parent;



    public Commit() {
        //initial commit
        this.timeStamp = timeDate.format(new Date(0));
        this.message = "initial commit";
        HEADCommit = this;
        File HeadCommi = Utils.join(Repository.GITLET_DIR, "HEADCommit");
        Utils.writeObject(HeadCommi,HEADCommit);
        this.hash = generateId();


        //main branch
        Commit main_branch = HEADCommit;
        File newBranch = Utils.join(Repository.BRANCHES_DIR,"main");
        Utils.writeObject(newBranch, main_branch);
        //

        //Setting the activebranch
        Commit activebranch = main_branch;
        File activebfile = Utils.join(Repository.GITLET_DIR,"activeBranch");
        Utils.writeObject(activebfile,"main");
        //Serializes this and sets it to activeBranch


        this.parent = null;
        this.SetupPersistenceforCommit();

    }


    public void updateHEAD(){ //resetting the head pointer to the active branch pointer
        File activebranchpointer = Utils.join(Repository.BRANCHES_DIR,getactive_pointer()); //Gets the current active pointer
        Commit activepoint = Utils.readObject(activebranchpointer, Commit.class); //Retrieves the current active pointer
        File HeadCommite = Utils.join(Repository.GITLET_DIR, "HeadCommit"); //Gets the Head Pointer
        Utils.writeObject(HeadCommite, activepoint); //Overwrites the headpointer to be the active pointer
    }

    public Commit loadCommit(String hashID){
        if (hashID == null){
            return null;
        }
        File commitfile = Utils.join(Repository.COMMITS_DIR, hashID);
        Commit commit = Utils.readObject(commitfile,Commit.class);
        return commit;
    }



    public Commit(String message) {
        //later commits
        this.message = message;
        this.timeStamp = timeDate.format(new Date());
        this.hash = generateId();
        this.takeSnapshot(); //takes the snapshot and saves a treemap p
        updateAP();
        updateHEAD();
        SetupPersistenceforCommit();




    }


    public void updateAP(){ //Serializes and updates the active pointer
        File activepfile = Utils.join(Repository.BRANCHES_DIR,getactive_pointer()); //gets the current (aka the old) active pointer
        Commit activepoint = Utils.readObject(activepfile, Commit.class);
        this.parentCommit = activepoint.hash;//sets the previous commit of the current commit to the original active pointer
        activepoint.nextCommit = this.hash;//sets the next commit of the current active pointer to this
        Commit ap2= activepoint;
        activepoint = this; //Somthing wrong ab here
        Commit pc= loadCommit(activepoint.parentCommit);
        String lbj = "j";





   //Serialization of the active pointer

        Utils.writeObject(activepfile,activepoint);


    }

    public Date firstTimeStamp(
    ) {
        Date timestamp = new Date(1970, 1, 1, 0, 0, 0);
        return timestamp;
    }

    public Commit getParentCommitnotS(){
        return loadCommit(this.parentCommit);
    }

    public Date timeStamp() {
        Date timestamp = new Date();
        return timestamp;
    }

    private String generateId() {
        // Generates unique ID for the commit using SHA-1 hash
        byte[] commitObj = Utils.serialize(this);
        return Utils.sha1(commitObj);
    }

    public void SetupPersistenceforCommit(){
        File Commmited = Utils.join(Repository.COMMITS_DIR, this.hash);
        Utils.writeObject(Commmited,this);
    }



//    public void save() {
//        Utils.writeObject(file, this);
//    }

    public void SetupPersistenceforCT(){
        File CommitFiles = Utils.join(Repository.GITLET_DIR,".treemap"+this.hash); //this.hash helps me with keeping track of what commit it is
        Utils.writeObject(CommitFiles,this.commit_tracker);
    }


    public String getactive_pointer(){
        File active = Utils.join(Repository.GITLET_DIR, "activeBranch");
        return Utils.readObject(active, String.class);
    }

    public void takeSnapshot(){
        File filesstagedTree = Utils.join(Repository.GITLET_DIR, "filesstagedTree");
        TreeMap<String,String> stagedFiles = Utils.readObject(filesstagedTree, TreeMap.class);
        for (String filename: stagedFiles.keySet()){
            Blobs blob = new Blobs(filename,this.hash);
            commit_tracker.put( filename,blob.getSHAIDofcommit()); //filename with the SHAID of the particular blob
            files_tracked.add(filename);

            //changed active pointer
            Commit activepointer = Utils.readObject((Utils.join(Repository.BRANCHES_DIR,getactive_pointer())), Commit.class);
            activepointer.files_added_for_a_branch.add(filename);
        }
        File filesstagedrmTree = Utils.join(Repository.GITLET_DIR, "filesstagedrmTree");
        TreeMap<String,String> stagedFilesrm = Utils.readObject(filesstagedrmTree, TreeMap.class);
        for (String filename: stagedFilesrm.keySet()){
            Blobs blob = new Blobs(filename,this.hash);
            commit_tracker.put(filename,blob.getSHAIDofcommit()); //filename with the SHAID of the particular blob
            files_tracked.add(filename);

            //changed active pointer
            Commit activepointer = Utils.readObject((Utils.join(Repository.BRANCHES_DIR,getactive_pointer())), Commit.class);
            activepointer.files_added_for_a_branch.add(filename);
        }
        this.SetupPersistenceforCT();
    }




}