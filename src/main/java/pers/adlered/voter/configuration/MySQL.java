package pers.adlered.voter.configuration;

/**
 * If you form this values, SpringBoot will be check the database:
 * DATABASE NAME: Voter
 * And auto create tables if not exist.
 * BUT FIRST: Fill in below values, and CREATE A DATABASE NAMED "Voter", then start the project.
 * Every tables will be auto generate.
 */
public class MySQL {
    public static String IP = "127.0.0.1";
    public static Integer Port = 3306;
    public static String Username = "adler";
    public static String Password = "six";
}
