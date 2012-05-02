First runing
1.create mydatabase in mysql.
2.change content in persistence.xml in src\main\javaMETA-INF from
<property name="hibernate.hbm2ddl.auto" value="validate" /> to <property name="hibernate.hbm2ddl.auto" value="create" />
Then start tomcat.
This is for hibernate to initiate the database tables.
3.run mvn build with parameter: 
mvn eclipse:clean eclipse:eclipse -Dwtpversion=2.0
mvn install -DskipTests

4. run sql script in mydatabase.sql to generate the data.(These are just random meanningless datas).



xml webservices: 
http://localhost:8080/quizx/quizservice/categorys
http://localhost:8080/quizx/quizservice/quiz/1

admin page: 
http://localhost:8080/quizx/quizservice/categoryspage

- display time in Oracle sql developer: 
alter session set nls_date_format = 'dd/mm/yyyy HH24:MI:SS'; 
OR go to sqldeveloper "preferences--> DATABASE-->NLS Parameters --> change "date format" to "dd/mm/yyyy HH24:MI:SS"







Setting up a local database can greatly speed up database build times.

    Oracle:
        Login to XP as admin user
        Locate the Oracle installer at http://triis104:8080/share/apps/Oracle
        Install Oracle 10.2.0.3.0 into c:\apps\oraclexe
        Go to Start menu and launch “Go To Database Home Page” from “Oracle Database 10G Express Edition” menu item
        Login as SYS and add a new user. Give the user all rights.
        Launch sqlplus in a cmd prompt using this command:

        sqlplus "?/? AS SYSDBA"

        Run the following commands in the SQL window (replace

        <CURAM_USER>

CREATE ROLE "CURAM_SERVER";
GRANT RESOURCE TO "CURAM_SERVER";

CREATE VIEW v$pending_xatrans$ AS
(SELECT global_tran_fmt, global_foreign_id, branch_id
   FROM   sys.pending_trans$ tran, sys.pending_sessions$ sess
   WHERE  tran.local_tran_id = sess.local_tran_id
     AND    tran.state != 'collecting'
     AND    BITAND(TO_NUMBER(tran.session_vector),
                   POWER(2, (sess.session_id - 1))) = sess.session_id);

CREATE VIEW v$xatrans$ AS
(((SELECT k2gtifmt, k2gtitid_ext, k2gtibid
   FROM x$k2gte2
   WHERE  k2gterct=k2gtdpct)
 MINUS
  SELECT global_tran_fmt, global_foreign_id, branch_id
   FROM   v$pending_xatrans$)
UNION
 SELECT global_tran_fmt, global_foreign_id, branch_id
   FROM   v$pending_xatrans$);

GRANT SELECT ON V$XATRANS$ TO PUBLIC;
GRANT SELECT ON PENDING_TRANS$ TO PUBLIC;
GRANT SELECT ON DBA_2PC_PENDING TO PUBLIC;
GRANT SELECT ON DBA_PENDING_TRANSACTIONS TO PUBLIC;
GRANT EXECUTE ON DBMS_SYSTEM TO CURAM_SERVER;
GRANT "CONNECT", "CURAM_SERVER", UNLIMITED TABLESPACE TO "<CURAM_USER>";