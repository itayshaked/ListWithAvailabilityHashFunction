import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.FileNotFoundException;
/**This class represents a data structure based on Object List with availability function based on hash function.
 * @author Itay Shaked

 * **/
public class Structure {
    private LinkedList<String> struc;
    private byte[] T;
    private final int Kfunc;
/**
 * Build Function
 * @param m Size of byte array.
 * @param k Amount of hash functions to operate.
 * Builds an empty list and m size byte array with all values equals to 0. **/
    public Structure(int m,int k){
        T=new byte[m];
        for (int i = 0; i < T.length; i++) {
            T[i]=0;
        }
        this.Kfunc=k;
        this.struc =new LinkedList<String>();
    }
/**
 *gets strings from file "ObjectsToAdd.txt",divide it to "words"(objects)and adds to the structure.
 * "word" counts as one if its between ','.
 * <p>
 * if we assume that c is the number of charcaters in the file and n is the number of words.
 *  and
 *      if we assume that a word can be not longer than
 *          c1(constant) then function runs on O(c+n*k)<=c1*O(n)+O(n*k) (k is the number of hash functions decided by the user)
 *
 * therefore this function runs on O(n*k) for the worst case.
 * </p>
 * **/
    public void addFromFile(){
        try {
            File objectFile = new File("ObjectsToAdd.txt");
            Scanner reader = new Scanner(objectFile);
            String toadd="";//this variable gathers the chars till the ','.
            String next = reader.next()+",";//this string variable is the entire file in one string.
            for (int i = 0; i < next.length(); i++) {//toadd variable gathers chars to become a word.
                //this loop runs O(c) when c is the number of characters in the file!.
                if (next.charAt(i) != ',') {
                    toadd=toadd+next.charAt(i);
                }
                //toadd is now an "Object"(word)
                else {
                    //this loop runs o(k) when k is decided by the user.(usually will not be big(1000 or more)
                    for (int j = 0; j <Kfunc; j++) {//byte array gets 1 in k indexes which being decided by hashFuncKBased().
                        T[hashFuncKBased(toadd,j)]=1;
                    }
                    struc.add(toadd);//adds to structure the object
                    toadd="";//ready for another iteration.
                }
            }
            reader.close();
        }

        catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            e.printStackTrace();
        }

    }
    /**
     * Gets string from "ObjectsToCheck.txt" file , divides it to "words"(objects) and checks
     * in the byte array (which was filled by objects going through k hashFuncKBased()) if the
     * indexes of of each word going through k hashFuncKBased() match the values in the byte array(all need to be 1 for the object to be found in the structure)*/
    public void checkFromFile(){
        try {
            File objectFile = new File("ObjectsToCheck.txt");
            Scanner reader2 = new Scanner(objectFile);
            String tocheck="";
            String next = reader2.next()+",";
            for (int i = 0; i < next.length(); i++) {
                if (next.charAt(i) != ',') {
                    tocheck=tocheck+next.charAt(i);
                }
                else {
                    boolean inside=true;
                    for (int j = 0; j <Kfunc; j++) {//runs O(k) when k is choosed by user
                       if(T[hashFuncKBased(tocheck,j)]==0){//this means that the object was not added or else all the hashFuncKBased() values indexes were 1.

                           inside=false;
                       }
                    }
                    if(inside)
                    System.out.println(tocheck +" was found in the structure");
                    else System.out.println(tocheck+ " was not found in the structure");
                    tocheck="";
                }
            }
            reader2.close();
        }

        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * This function transform and object to an absolute hashCode(between 0 to Max.Integer).
     *      * then it represents the equation:<p> ((|x|%m)*k)%m </p>whereas x is the absolute hashCode.
     *      * if we assume that hashCode runtime is O(1) then this function runtime is O(1)
     * <p>
     * @param input is an Object
     * @param k is a variable that changes the function according to it.
     *
     *@return an Integer between 0 to m
     * </p> */
    public int hashFuncKBased(Object input,int k){
        int inputcode=Math.abs(input.hashCode());
        inputcode=inputcode%T.length;
        inputcode=inputcode*k;
        inputcode=inputcode%T.length;
        return inputcode;
    }


}
