package com.api.vaccinationmanagement;

import com.api.vaccinationmanagement.model.*;
import com.api.vaccinationmanagement.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class InitDatabase {
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private SickRepo sickRepo;
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private VaccineRepo vaccineRepo;
    @Autowired
    private VMRepo vmRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void initDatabase(){
        if(roleRepo.findRoleModelByCode("ROLE_ADMIN").isEmpty()){
            initRole();
            initEmployee();
            initSick();
            initVaccine();
            initPatient();
            initVM();
        }
    }

    public void initRole() {
        createRoleIfNotExists("ROLE_ADMIN", "Administrator");
        createRoleIfNotExists("ROLE_MANAGER", "Manager");
        createRoleIfNotExists("ROLE_EMPLOYEE", "Employee");
    }

    public void initEmployee() {
        createEmployeeIfNotExists(
                "admin@gmail.com",
                "admin",
                "ROLE_ADMIN",
                "0000"
        );
        createEmployeeIfNotExists(
                "manager@gmail.com",
                "manager",
                "ROLE_MANAGER",
                "0000"
        );
        createEmployeeIfNotExists(
                "employee_a@gmail.com",
                "employee_a",
                "ROLE_EMPLOYEE",
                "0001"
        );
        createEmployeeIfNotExists(
                "employee_b@gmail.com",
                "employee_b",
                "ROLE_EMPLOYEE",
                "0001-0001"
        );
        createEmployeeIfNotExists(
                "employee_c@gmail.com",
                "employee_c",
                "ROLE_EMPLOYEE",
                "0001-0001-0001"
        );
    }

    public void initSick() {
        createSickIfNotExists("COVID", "COVID-19");
        createSickIfNotExists("H5N1", "H5N1");
        createSickIfNotExists("Tetanus", "Tetanus");
        createSickIfNotExists("Westleigh", "Focused logistical strategy");
        createSickIfNotExists("Leda", "Multi-channelled executive migration");
        createSickIfNotExists("Doralynne", "Secured secondary standardization");
        createSickIfNotExists("Karol", "Mandatory explicit functionalities");
        createSickIfNotExists("Jaime", "Inverse intangible software");
        createSickIfNotExists("Elayne", "Networked stable application");
        createSickIfNotExists("Nadeen", "Total optimizing migration");
    }

    public void initVaccine() {
        createVaccineIfNotExists("AstraZeneca", "AstraZeneca", 12, 99, "Anh", 1);
        createVaccineIfNotExists("Pfizer", "Pfizer", 12, 99, "Mỹ", 1);
        createVaccineIfNotExists("Sinopharm", "Sinopharm", 18, 99, "Trung Quốc", 1);
        createVaccineIfNotExists("Foclivia", "Foclivia", 18, 60, "Italy", 2);
        createVaccineIfNotExists("DTaP", "DTaP", 0, 7, "Mỹ", 3);
        createVaccineIfNotExists("Joane", "Programmable actuating ability", 17, 75, "Anh", 12);
        createVaccineIfNotExists("Nikaniki", "Upgradable next generation artificial intelligence", 0, 87, "Nhật Bản", 10);
        createVaccineIfNotExists("Heath", "Quality-focused discrete superstructure", 13, 88, "Trung Quốc", 12);
        createVaccineIfNotExists("Eddie", "De-engineered next generation info-mediaries", 3, 92, "Anh", 9);
        createVaccineIfNotExists("Fonzie", "Configurable disintermediate implementation", 1, 59, "Trung Quốc", 8);
        createVaccineIfNotExists("Helena", "Ergonomic national approach", 6, 73, "Anh", 9);
        createVaccineIfNotExists("Alisha", "Diverse value-added initiative", 15, 61, "Anh", 10);
        createVaccineIfNotExists("Wren", "Mandatory mission-critical forecast", 13, 69, "Nhật Bản", 11);
        createVaccineIfNotExists("Huberto", "Versatile composite workforce", 18, 60, "Mỹ", 13);
        createVaccineIfNotExists("Nani", "Team-oriented modular Graphical User Interface", 13, 89, "Trung Quốc", 11);
        createVaccineIfNotExists("Far", "Multi-channelled asymmetric collaboration", 6, 59, "Anh", 13);
        createVaccineIfNotExists("Wileen", "Inverse foreground orchestration", 13, 70, "Trung Quốc", 10);
        createVaccineIfNotExists("Karoline", "Visionary dynamic knowledge user", 9, 90, "Anh", 12);
        createVaccineIfNotExists("Zandra", "Business-focused content-based frame", 8, 67, "Mỹ", 10);
        createVaccineIfNotExists("Veronika", "Exclusive upward-trending database", 3, 66, "Nhật Bản", 11);
        createVaccineIfNotExists("Sophi", "Sharable cohesive orchestration", 7, 74, "Mỹ", 13);
        createVaccineIfNotExists("Feliza", "User-centric next generation collaboration", 12, 52, "Trung Quốc", 12);
        createVaccineIfNotExists("Silvana", "Grass-roots multimedia encoding", 2, 91, "Trung Quốc", 8);
        createVaccineIfNotExists("Bern", "Intuitive discrete system engine", 6, 74, "Nhật Bản", 11);
        createVaccineIfNotExists("Tabbi", "Devolved needs-based internet solution", 10, 65, "Mỹ", 7);
        createVaccineIfNotExists("Gibbie", "Streamlined 24/7 functionalities", 8, 87, "Trung Quốc", 7);
        createVaccineIfNotExists("Lauretta", "Team-oriented optimizing internet solution", 11, 50, "Mỹ", 12);
        createVaccineIfNotExists("Edvard", "Reactive logistical ability", 5, 99, "Nhật Bản", 7);
        createVaccineIfNotExists("Sallee", "Profound 6th generation concept", 9, 64, "Mỹ", 10);
        createVaccineIfNotExists("Cordell", "Switchable encompassing flexibility", 7, 64, "Mỹ", 12);
        createVaccineIfNotExists("Lin", "Reverse-engineered web-enabled intranet", 1, 67, "Mỹ", 7);
        createVaccineIfNotExists("Raul", "Multi-channelled 6th generation project", 8, 83, "Mỹ", 13);
        createVaccineIfNotExists("Massimiliano", "Innovative intangible forecast", 11, 75, "Mỹ", 7);
        createVaccineIfNotExists("Bink", "Polarised optimal core", 12, 56, "Mỹ", 10);
        createVaccineIfNotExists("Guntar", "Decentralized systemic forecast", 8, 88, "Nhật Bản", 12);
    }

    public void initPatient() {
        createPatientIfNotExists("kgeffinger0@prlog.org", "Philomena Pacheco", "737-460-4364", Timestamp.valueOf("2004-09-16 21:26:02"), "0002-0002-0001");
        createPatientIfNotExists("dstearnes0@boston.com", "Dillon Stearnes", "645-555-8979", Timestamp.valueOf("2010-10-29 20:55:57"), "0002-0002-0001");
        createPatientIfNotExists("hcornely1@drupal.org", "Harli Cornely", "136-892-6305", Timestamp.valueOf("2004-08-13 00:20:08"), "0002-0002-0001");
        createPatientIfNotExists("edit Philomena Pacheco", "edit Philomena Pacheco", "737-460-4364", Timestamp.valueOf("2004-09-17 04:26:02"), "0001-0001-0002");
        createPatientIfNotExists("zure3@marketwatch.com", "Zulema Ure", "823-664-6745", Timestamp.valueOf("2000-06-01 04:47:35"), "0002-0001-0002");
        createPatientIfNotExists("aloveman4@google.com.hk", "Alfreda Loveman", "155-265-2195", Timestamp.valueOf("2009-08-08 07:49:03"), "0002-0001-0002");
        createPatientIfNotExists("lriquet5@51.la", "L;urette Riquet", "110-546-4119", Timestamp.valueOf("2010-03-03 00:22:51"), "0001-0001-0001");
        createPatientIfNotExists("pshoubridge6@pen.io", "Pansie Shoubridge", "626-503-5286", Timestamp.valueOf("2005-09-30 13:30:02"), "0002-0001-0001");
        createPatientIfNotExists("gsnufflebottom7@apple.com", "Giorgio Snufflebottom", "632-730-7366", Timestamp.valueOf("2001-02-17 16:52:41"), "0001-0001-0001");
        createPatientIfNotExists("cjutson8@indiatimes.com", "Correna Jutson", "426-531-9470", Timestamp.valueOf("2000-04-17 22:55:30"), "0002-0002-0001");
        createPatientIfNotExists("iportal9@networkadvertising.org", "Ilyse Portal", "721-480-4205", Timestamp.valueOf("2003-06-04 02:28:06"), "0001-0002-0002");
        createPatientIfNotExists("snorthropa@sitemeter.com", "Sanson Northrop", "444-428-1954", Timestamp.valueOf("2008-02-22 13:00:29"), "0001-0002-0002");
        createPatientIfNotExists("peasbieb@about.me", "Polly Easbie", "976-154-2164", Timestamp.valueOf("2000-11-26 16:17:44"), "0002-0001-0001");
        createPatientIfNotExists("khattoc@go.com", "Kameko Hatto", "773-950-3096", Timestamp.valueOf("2002-03-07 12:07:25"), "0001-0002-0002");
        createPatientIfNotExists("jpadrickd@desdev.cn", "Jourdain Padrick", "759-538-1482", Timestamp.valueOf("2009-09-11 02:22:56"), "0001-0001-0001");
        createPatientIfNotExists("vkymee@timesonline.co.uk", "Violette Kyme", "475-221-8055", Timestamp.valueOf("2003-10-28 09:03:39"), "0001-0002-0001");
        createPatientIfNotExists("zleechmanf@baidu.com", "Zacherie Leechman", "637-689-4564", Timestamp.valueOf("2007-11-06 08:52:44"), "0001-0001-0001");
        createPatientIfNotExists("ybrennandg@foxnews.com", "Yard Brennand", "624-865-8982", Timestamp.valueOf("2008-10-11 08:17:20"), "0001-0001-0001");
        createPatientIfNotExists("tstaggh@fastcompany.com", "Teodorico Stagg", "681-783-5427", Timestamp.valueOf("2005-10-15 13:36:15"), "0001-0001-0002");
        createPatientIfNotExists("zdaggi@mozilla.org", "Zacharia Dagg", "751-386-5187", Timestamp.valueOf("2008-05-30 05:21:45"), "0002-0001-0001");
        createPatientIfNotExists("mpuddlej@upenn.edu", "Marci Puddle", "628-356-9585", Timestamp.valueOf("2001-11-13 06:15:54"), "0001-0002-0002");
        createPatientIfNotExists("mvivyank@blogger.com", "Mindy Vivyan", "925-306-4734", Timestamp.valueOf("2008-06-08 10:07:00"), "0002-0001-0001");
        createPatientIfNotExists("rabellsl@liveinternet.ru", "Rodolphe Abells", "708-950-2260", Timestamp.valueOf("2000-06-22 15:41:32"), "0001-0001-0002");
        createPatientIfNotExists("kporterm@com.com", "Kerrie Porter", "578-810-7772", Timestamp.valueOf("2010-09-13 10:45:11"), "0001-0002-0001");
        createPatientIfNotExists("amclaen@tripod.com", "Annamarie McLae", "658-123-9473", Timestamp.valueOf("2005-12-28 02:40:53"), "0002-0002-0002");
        createPatientIfNotExists("boo@list-manage.com", "Becki O Mahony", "605-781-0068", Timestamp.valueOf("2009-11-27 06:28:55"), "0002-0001-0002");
        createPatientIfNotExists("bgresswellp@google.co.jp", "Brandy Gresswell", "937-257-8573", Timestamp.valueOf("2001-05-05 19:36:39"), "0001-0002-0002");
        createPatientIfNotExists("kwedgbrowq@weebly.com", "Karyn Wedgbrow", "412-867-1414", Timestamp.valueOf("2001-06-30 00:46:57"), "0001-0001-0001");
        createPatientIfNotExists("sshepeardr@skype.com", "Sabrina Shepeard", "952-620-4198", Timestamp.valueOf("2009-08-22 19:30:14"), "0001-0001-0002");
        createPatientIfNotExists("amullords@independent.co.uk", "Aylmar Mullord", "855-652-9681", Timestamp.valueOf("2007-08-14 22:28:24"), "0001-0002-0002");
        createPatientIfNotExists("hweinst@youtube.com", "Horace Weins", "471-671-9829", Timestamp.valueOf("2001-06-17 08:55:04"), "0002-0001-0001");
        createPatientIfNotExists("rgulcu@list-manage.com", "Reinaldos Gulc", "514-769-6331", Timestamp.valueOf("2008-01-22 18:16:00"), "0001-0001-0001");
        createPatientIfNotExists("sgynnev@meetup.com", "Sapphire Gynne", "782-304-8567", Timestamp.valueOf("2008-03-05 14:16:16"), "0002-0002-0001");
        createPatientIfNotExists("ecromblehomew@ow.ly", "Emogene Cromblehome", "965-442-4847", Timestamp.valueOf("2000-02-06 07:09:23"), "0001-0001-0002");
        createPatientIfNotExists("jsparwellx@shutterfly.com", "Jobye Sparwell", "339-689-5153", Timestamp.valueOf("2003-11-03 07:58:55"), "0001-0001-0002");
        createPatientIfNotExists("dbovisy@tiny.cc", "Dimitri Bovis", "244-339-6905", Timestamp.valueOf("2009-01-30 05:10:44"), "0002-0001-0002");
        createPatientIfNotExists("sbailesz@sun.com", "Sibilla Bailes", "496-449-5178", Timestamp.valueOf("2003-01-15 02:19:00"), "0001-0001-0002");
        createPatientIfNotExists("mprydden10@fema.gov", "Marthe Prydden", "321-270-3335", Timestamp.valueOf("2005-08-05 20:04:04"), "0002-0002-0002");
        createPatientIfNotExists("lbaltrushaitis11@aboutads.info", "Lib Baltrushaitis", "901-576-2088", Timestamp.valueOf("2005-12-03 04:29:36"), "0001-0001-0001");
        createPatientIfNotExists("ngooday12@blinklist.com", "Norton Gooday", "547-391-2761", Timestamp.valueOf("2007-11-15 12:17:28"), "0002-0002-0002");
        createPatientIfNotExists("jord13@yellowbook.com", "Jessy Ord", "970-369-6258", Timestamp.valueOf("2000-11-17 02:04:46"), "0001-0002-0001");
        createPatientIfNotExists("lfrane14@go.com", "Lynett Frane", "467-709-4444", Timestamp.valueOf("2009-09-11 15:50:17"), "0001-0001-0002");
        createPatientIfNotExists("cgoodred15@thetimes.co.uk", "Clint Goodred", "618-298-2678", Timestamp.valueOf("2009-10-26 05:26:06"), "0002-0001-0001");
        createPatientIfNotExists("jabotson16@mlb.com", "Jillian Abotson", "959-715-8111", Timestamp.valueOf("2010-12-13 14:30:31"), "0001-0001-0001");
        createPatientIfNotExists("mmacneachtain17@alibaba.com", "Marijn MacNeachtain", "757-357-3547", Timestamp.valueOf("2000-11-01 06:51:21"), "0002-0001-0001");
        createPatientIfNotExists("ehrihorovich18@ucoz.ru", "Ephrayim Hrihorovich", "543-329-7025", Timestamp.valueOf("2000-11-24 17:43:38"), "0001-0001-0002");
        createPatientIfNotExists("gstonestreet19@cbslocal.com", "Gun Stonestreet", "222-382-0093", Timestamp.valueOf("2010-01-03 17:42:31"), "0002-0001-0002");
        createPatientIfNotExists("lhalle1a@artisteer.com", "Lucky Halle", "522-615-5375", Timestamp.valueOf("2007-08-19 23:28:54"), "0002-0001-0001");
        createPatientIfNotExists("rbill1b@friendfeed.com", "Rriocard Bill", "754-929-8006", Timestamp.valueOf("2005-07-26 18:35:35"), "0002-0001-0002");
        createPatientIfNotExists("lpauel1c@bloglovin.com", "Lazaro Pauel", "786-430-4364", Timestamp.valueOf("2007-10-02 08:02:12"), "0002-0002-0001");
        createPatientIfNotExists("kgeffinger0@prlog.org", "new Philomena Pacheco", "737-460-4364", Timestamp.valueOf("2004-09-16 21:26:02"), "0001-0002-0001");
        createPatientIfNotExists("kgeffinger0@prlog.org", "new 22 Philomena Pacheco", "737-460-4364", Timestamp.valueOf("2004-09-16 21:26:02"), "0001-0001-0002");
        createPatientIfNotExists("nnnnnngantiger@gmail.com", "testa", "737-460-4364", Timestamp.valueOf("2004-09-16 21:26:02"), "0002-0001-0002");
        createPatientIfNotExists("nnngantiger@gmail.com", "testb", "737-460-4365", Timestamp.valueOf("2004-09-17 21:26:02"), "0002-0001-0001");
        createPatientIfNotExists("nnnnngantiger@gmail.com", "testc", "737-460-4366", Timestamp.valueOf("2004-09-18 21:26:02"), "0002-0001-0001");
        createPatientIfNotExists("nnngantiger@gmail.com", "testb", "737-460-4365", Timestamp.valueOf("2004-09-17 21:26:02"), "0002-0001-0001");
        createPatientIfNotExists("nnnnngantiger@gmail.com", "testc", "737-460-4366", Timestamp.valueOf("2004-09-18 21:26:02"), "0002-0001-0001");
        createPatientIfNotExists("nnngantiger@gmail.com", "testbbbbbbb", "737-460-4365", Timestamp.valueOf("2004-09-17 21:26:02"), "0002-0001-0001");
        createPatientIfNotExists("nnnnngantiger@gmail.com", "testddddddddd", "737-460-4366", Timestamp.valueOf("2004-09-18 21:26:02"), "0002-0001-0001");
        createPatientIfNotExists("nnnnngantiger@gmail.com", "testddddddddd", "737-460-4366", Timestamp.valueOf("2004-09-18 21:26:02"), "0002-0001-0001");
        createPatientIfNotExists("nnnnnngantiger@gmail.com", "testaaaaaaaa", "737-460-4364", Timestamp.valueOf("2004-09-16 21:26:02"), "0002-0001-0002");
        createPatientIfNotExists("nnngantiger@gmail.com", "testbbbbbbb", "737-460-4365", Timestamp.valueOf("2004-09-17 21:26:02"), "0002-0001-0001");
        createPatientIfNotExists("nnnnngantiger@gmail.com", "testcccccccc", "737-460-4366", Timestamp.valueOf("2004-09-18 21:26:02"), "0002-0001-0001");
        createPatientIfNotExists("nnnnnngantiger@gmail.com", "testaaaaaaaa", "737-460-4364", Timestamp.valueOf("2004-09-16 21:26:02"), "0002-0001-0002");
        createPatientIfNotExists("nnnnnngantiger@gmail.com", "testaaaaaaaa", "737-460-4364", Timestamp.valueOf("2004-09-16 21:26:02"), "0002-0001-0002");
        createPatientIfNotExists("nnnnnngantiger@gmail.com", "testaaaaaaaa", "737-460-4364", Timestamp.valueOf("2004-09-16 21:26:02"), "0002-0001-0002");
        createPatientIfNotExists("nnngantiger@gmail.com", "testbbbbbbb", "737-460-4365", Timestamp.valueOf("2004-09-17 21:26:02"), "0002-0001-0001");
        createPatientIfNotExists("nnnnngantiger@gmail.com", "testcccccccc", "737-460-4366", Timestamp.valueOf("2004-09-18 21:26:02"), "0002-0001-0001");
        createPatientIfNotExists("nnnnnngantiger@gmail.com", "testaaaaaaaa", "737-460-4364", Timestamp.valueOf("2004-09-16 21:26:02"), "0002-0001-0002");
        createPatientIfNotExists("nnngantiger@gmail.com", "testbbbbbbb", "737-460-4365", Timestamp.valueOf("2004-09-17 21:26:02"), "0002-0001-0001");
        createPatientIfNotExists("nnnnngantiger@gmail.com", "testcccccccc", "737-460-4366", Timestamp.valueOf("2004-09-18 21:26:02"), "0002-0001-0001");
        createPatientIfNotExists("nnnnnngantiger@gmail.com", "testaaaaaaaa", "737-460-4364", Timestamp.valueOf("2004-09-16 21:26:02"), "0002-0001-0002");
    }

    public void initVM() {
        createVMIfNotExists(Timestamp.valueOf("2023-09-08 23:17:13"), 1, 1, 1);
        createVMIfNotExists(Timestamp.valueOf("2010-02-16 16:33:19"), 39, 7, 28);
        createVMIfNotExists(Timestamp.valueOf("2011-08-13 10:30:36"), 36, 7, 28);
        createVMIfNotExists(Timestamp.valueOf("2011-12-20 04:15:55"), 31, 7, 27);
        createVMIfNotExists(Timestamp.valueOf("2010-04-13 12:44:29"), 19, 7, 27);
        createVMIfNotExists(Timestamp.valueOf("2010-08-24 19:23:43"), 27, 7, 35);
        createVMIfNotExists(Timestamp.valueOf("2010-02-06 11:31:23"), 46, 7, 28);
        createVMIfNotExists(Timestamp.valueOf("2011-02-16 21:41:42"), 38, 7, 27);
        createVMIfNotExists(Timestamp.valueOf("2010-01-09 09:56:54"), 2, 7, 28);
        createVMIfNotExists(Timestamp.valueOf("2011-12-13 00:10:59"), 12, 7, 27);
        createVMIfNotExists(Timestamp.valueOf("2010-11-09 10:35:28"), 41, 7, 27);
        createVMIfNotExists(Timestamp.valueOf("2010-02-10 09:16:15"), 40, 7, 27);
        createVMIfNotExists(Timestamp.valueOf("2011-01-01 22:41:20"), 7, 7, 35);
        createVMIfNotExists(Timestamp.valueOf("2011-04-13 11:43:02"), 16, 7, 30);
        createVMIfNotExists(Timestamp.valueOf("2011-02-09 22:09:04"), 30, 7, 27);
        createVMIfNotExists(Timestamp.valueOf("2010-12-25 13:30:59"), 9, 7, 28);
        createVMIfNotExists(Timestamp.valueOf("2011-02-18 03:00:36"), 35, 7, 27);
        createVMIfNotExists(Timestamp.valueOf("2010-09-08 16:23:33"), 8, 7, 28);
        createVMIfNotExists(Timestamp.valueOf("2010-04-11 23:09:38"), 22, 7, 28);
        createVMIfNotExists(Timestamp.valueOf("2010-10-20 20:42:36"), 32, 7, 28);
        createVMIfNotExists(Timestamp.valueOf("2011-06-20 10:18:53"), 39, 7, 35);
        createVMIfNotExists(Timestamp.valueOf("2011-12-11 10:51:48"), 47, 8, 12);
        createVMIfNotExists(Timestamp.valueOf("2011-12-16 17:41:46"), 37, 8, 25);
        createVMIfNotExists(Timestamp.valueOf("2011-10-08 00:08:25"), 26, 8, 25);
        createVMIfNotExists(Timestamp.valueOf("2010-03-02 22:20:51"), 28, 8, 25);
        createVMIfNotExists(Timestamp.valueOf("2011-03-01 07:14:25"), 18, 8, 12);
        createVMIfNotExists(Timestamp.valueOf("2010-07-18 16:06:18"), 24, 8, 12);
        createVMIfNotExists(Timestamp.valueOf("2010-10-07 23:48:46"), 7, 8, 25);
        createVMIfNotExists(Timestamp.valueOf("2010-07-24 00:56:07"), 33, 8, 12);
        createVMIfNotExists(Timestamp.valueOf("2011-11-08 04:55:11"), 46, 8, 25);
        createVMIfNotExists(Timestamp.valueOf("2011-11-04 04:09:28"), 50, 8, 25);
        createVMIfNotExists(Timestamp.valueOf("2010-06-18 06:07:53"), 14, 8, 25);
        createVMIfNotExists(Timestamp.valueOf("2011-11-23 03:51:49"), 25, 8, 25);
        createVMIfNotExists(Timestamp.valueOf("2011-01-25 21:23:18"), 48, 8, 12);
        createVMIfNotExists(Timestamp.valueOf("2010-04-20 02:14:03"), 5, 8, 25);
        createVMIfNotExists(Timestamp.valueOf("2010-12-07 19:08:22"), 41, 8, 25);
        createVMIfNotExists(Timestamp.valueOf("2010-08-06 09:57:10"), 41, 8, 12);
        createVMIfNotExists(Timestamp.valueOf("2010-04-23 02:57:55"), 16, 8, 25);
        createVMIfNotExists(Timestamp.valueOf("2011-07-29 12:26:59"), 42, 8, 25);
        createVMIfNotExists(Timestamp.valueOf("2010-07-04 01:49:04"), 27, 8, 25);
        createVMIfNotExists(Timestamp.valueOf("2010-03-09 20:48:36"), 45, 8, 12);
        createVMIfNotExists(Timestamp.valueOf("2010-07-30 14:12:06"), 49, 9, 13);
        createVMIfNotExists(Timestamp.valueOf("2011-08-22 08:43:33"), 7, 9, 11);
        createVMIfNotExists(Timestamp.valueOf("2011-10-31 19:22:09"), 7, 9, 13);
        createVMIfNotExists(Timestamp.valueOf("2010-07-01 14:42:20"), 49, 9, 13);
        createVMIfNotExists(Timestamp.valueOf("2011-11-03 11:37:56"), 44, 9, 11);
        createVMIfNotExists(Timestamp.valueOf("2011-07-01 01:50:29"), 43, 9, 11);
        createVMIfNotExists(Timestamp.valueOf("2011-06-08 22:46:16"), 49, 9, 11);
        createVMIfNotExists(Timestamp.valueOf("2010-09-02 22:21:46"), 40, 9, 11);
        createVMIfNotExists(Timestamp.valueOf("2010-07-15 01:46:31"), 48, 9, 13);
        createVMIfNotExists(Timestamp.valueOf("2010-11-14 00:04:39"), 12, 9, 13);
        createVMIfNotExists(Timestamp.valueOf("2011-10-20 01:21:10"), 26, 9, 13);
        createVMIfNotExists(Timestamp.valueOf("2011-10-07 12:07:54"), 42, 9, 11);
        createVMIfNotExists(Timestamp.valueOf("2010-05-13 14:04:22"), 50, 9, 13);
        createVMIfNotExists(Timestamp.valueOf("2011-11-20 20:31:23"), 45, 9, 11);
        createVMIfNotExists(Timestamp.valueOf("2010-08-02 14:06:57"), 43, 9, 11);
        createVMIfNotExists(Timestamp.valueOf("2010-07-13 10:46:21"), 30, 9, 11);
        createVMIfNotExists(Timestamp.valueOf("2010-07-11 13:48:13"), 1, 9, 13);
        createVMIfNotExists(Timestamp.valueOf("2010-10-30 07:09:03"), 15, 9, 13);
        createVMIfNotExists(Timestamp.valueOf("2010-09-16 09:35:25"), 16, 9, 11);
        createVMIfNotExists(Timestamp.valueOf("2010-11-03 05:42:16"), 26, 9, 11);
        createVMIfNotExists(Timestamp.valueOf("2010-09-07 15:36:05"), 26, 10, 31);
        createVMIfNotExists(Timestamp.valueOf("2010-11-28 02:19:28"), 35, 10, 14);
        createVMIfNotExists(Timestamp.valueOf("2010-11-15 08:57:09"), 47, 10, 19);
        createVMIfNotExists(Timestamp.valueOf("2010-12-28 22:30:58"), 36, 10, 31);
        createVMIfNotExists(Timestamp.valueOf("2011-03-23 03:04:39"), 40, 10, 14);
        createVMIfNotExists(Timestamp.valueOf("2010-06-17 04:29:49"), 28, 10, 31);
        createVMIfNotExists(Timestamp.valueOf("2010-02-11 05:32:05"), 44, 10, 36);
        createVMIfNotExists(Timestamp.valueOf("2011-04-11 03:54:40"), 10, 10, 9);
        createVMIfNotExists(Timestamp.valueOf("2010-08-29 19:10:18"), 8, 10, 19);
        createVMIfNotExists(Timestamp.valueOf("2011-03-25 05:52:39"), 21, 10, 21);
        createVMIfNotExists(Timestamp.valueOf("2011-03-06 16:19:43"), 5, 10, 36);
        createVMIfNotExists(Timestamp.valueOf("2010-09-06 17:03:54"), 43, 10, 31);
        createVMIfNotExists(Timestamp.valueOf("2010-12-21 02:24:37"), 19, 10, 9);
        createVMIfNotExists(Timestamp.valueOf("2010-11-29 12:49:32"), 50, 10, 31);
        createVMIfNotExists(Timestamp.valueOf("2010-10-28 06:15:52"), 3, 10, 14);
        createVMIfNotExists(Timestamp.valueOf("2011-07-06 22:03:57"), 47, 10, 9);
        createVMIfNotExists(Timestamp.valueOf("2010-11-16 23:23:57"), 7, 10, 36);
        createVMIfNotExists(Timestamp.valueOf("2010-04-24 09:29:24"), 8, 10, 9);
        createVMIfNotExists(Timestamp.valueOf("2010-02-08 23:35:26"), 22, 10, 14);
        createVMIfNotExists(Timestamp.valueOf("2010-04-23 04:01:08"), 7, 10, 21);
        createVMIfNotExists(Timestamp.valueOf("2011-05-01 12:23:56"), 23, 11, 15);
        createVMIfNotExists(Timestamp.valueOf("2010-08-04 05:18:20"), 20, 11, 15);
        createVMIfNotExists(Timestamp.valueOf("2010-10-06 23:57:57"), 47, 11, 22);
        createVMIfNotExists(Timestamp.valueOf("2010-04-05 23:14:23"), 33, 11, 15);
        createVMIfNotExists(Timestamp.valueOf("2011-04-02 04:08:34"), 24, 11, 26);
        createVMIfNotExists(Timestamp.valueOf("2010-09-17 08:54:16"), 7, 11, 15);
        createVMIfNotExists(Timestamp.valueOf("2010-06-26 11:06:25"), 4, 11, 26);
        createVMIfNotExists(Timestamp.valueOf("2010-06-06 14:55:17"), 41, 11, 17);
        createVMIfNotExists(Timestamp.valueOf("2010-11-23 00:06:47"), 21, 11, 22);
        createVMIfNotExists(Timestamp.valueOf("2010-07-15 01:50:18"), 11, 11, 22);
        createVMIfNotExists(Timestamp.valueOf("2010-11-28 00:02:34"), 39, 11, 17);
        createVMIfNotExists(Timestamp.valueOf("2011-03-22 18:36:58"), 19, 11, 15);
        createVMIfNotExists(Timestamp.valueOf("2010-05-01 19:52:37"), 49, 11, 26);
        createVMIfNotExists(Timestamp.valueOf("2010-05-09 02:53:09"), 25, 11, 26);
        createVMIfNotExists(Timestamp.valueOf("2011-05-20 23:29:17"), 34, 11, 17);
        createVMIfNotExists(Timestamp.valueOf("2010-11-29 02:26:33"), 34, 11, 26);
        createVMIfNotExists(Timestamp.valueOf("2011-12-04 22:31:04"), 41, 11, 15);
        createVMIfNotExists(Timestamp.valueOf("2010-01-08 20:36:17"), 41, 11, 22);
        createVMIfNotExists(Timestamp.valueOf("2010-12-02 02:58:04"), 46, 11, 26);
        createVMIfNotExists(Timestamp.valueOf("2010-12-04 07:35:21"), 13, 11, 26);
        createVMIfNotExists(Timestamp.valueOf("2010-02-26 22:07:01"), 32, 12, 10);
        createVMIfNotExists(Timestamp.valueOf("2010-08-25 21:03:07"), 14, 12, 32);
        createVMIfNotExists(Timestamp.valueOf("2011-10-18 02:20:56"), 29, 12, 29);
        createVMIfNotExists(Timestamp.valueOf("2011-06-30 06:49:38"), 37, 12, 37);
        createVMIfNotExists(Timestamp.valueOf("2010-10-21 06:47:45"), 40, 12, 10);
        createVMIfNotExists(Timestamp.valueOf("2010-09-23 16:27:01"), 17, 12, 8);
        createVMIfNotExists(Timestamp.valueOf("2010-01-20 20:31:49"), 14, 12, 24);
        createVMIfNotExists(Timestamp.valueOf("2011-02-17 04:42:23"), 48, 12, 29);
        createVMIfNotExists(Timestamp.valueOf("2011-07-06 11:15:25"), 34, 12, 29);
        createVMIfNotExists(Timestamp.valueOf("2011-11-04 07:48:34"), 42, 12, 24);
        createVMIfNotExists(Timestamp.valueOf("2010-05-15 06:44:54"), 44, 12, 29);
        createVMIfNotExists(Timestamp.valueOf("2010-12-16 05:40:05"), 3, 12, 29);
        createVMIfNotExists(Timestamp.valueOf("2011-07-20 10:57:30"), 36, 12, 20);
        createVMIfNotExists(Timestamp.valueOf("2011-04-18 17:59:26"), 22, 12, 37);
        createVMIfNotExists(Timestamp.valueOf("2010-12-13 02:54:54"), 33, 12, 24);
        createVMIfNotExists(Timestamp.valueOf("2011-03-19 06:06:24"), 27, 12, 24);
        createVMIfNotExists(Timestamp.valueOf("2010-08-02 23:56:53"), 48, 12, 32);
        createVMIfNotExists(Timestamp.valueOf("2010-05-13 16:18:08"), 22, 12, 10);
        createVMIfNotExists(Timestamp.valueOf("2010-05-26 01:44:11"), 5, 12, 29);
        createVMIfNotExists(Timestamp.valueOf("2011-10-01 18:20:32"), 45, 12, 29);
        createVMIfNotExists(Timestamp.valueOf("2011-10-30 07:09:03"), 15, 9, 13);
    }

    private void createRoleIfNotExists(String code, String name) {
        Optional<RoleModel> existingRole = roleRepo.findRoleModelByCode(code);
        if (existingRole.isEmpty()) {
            RoleModel role = new RoleModel();
            role.setCode(code);
            role.setName(name);
            roleRepo.save(role);
        }
    }

    private void createEmployeeIfNotExists(String email, String fullname, String roleCode, String roleRegion) {
        Optional<EmployeeModel> existingEmployee = employeeRepo.findEmployeeModelByEmail(email);
        if (existingEmployee.isEmpty()) {
            Optional<RoleModel> role = roleRepo.findRoleModelByCode(roleCode);
            if (role.isPresent()) {
                EmployeeModel employee = EmployeeModel.builder()
                        .email(email)
                        .fullname(fullname)
                        .password(passwordEncoder.encode("123Abc"))
                        .enabled(true)
                        .roleRegion(roleRegion)
                        .roleModel(role.get())
                        .build();
                employeeRepo.save(employee);
            }
        }
    }

    private void createSickIfNotExists(String name, String description) {
        Optional<SickModel> sickModel = sickRepo.findSickModelByName(name);
        if (sickModel.isEmpty()) {
            SickModel model = new SickModel();
            model.setName(name);
            model.setDescription(description);

            sickRepo.save(model);
        }
    }

    private void createVaccineIfNotExists(String name, String description, Integer minAge, Integer maxAge, String madeIn, Integer sickId) {
        Optional<SickModel> sickModel = sickRepo.findById(sickId);
        if (sickModel.isPresent() && vaccineRepo.findVaccineModelByName(name).isEmpty()) {
            VaccineModel vaccineModel = new VaccineModel();
            vaccineModel.setName(name);
            vaccineModel.setDescription(description);
            vaccineModel.setMinAge(minAge);
            vaccineModel.setMaxAge(maxAge);
            vaccineModel.setMadeIn(madeIn);
            vaccineModel.setSickModel(sickModel.get());

            vaccineRepo.save(vaccineModel);
        }
    }

    private void createPatientIfNotExists(String email, String fullname, String phone, Timestamp birthDate, String addressCode) {
        List<PatientModel> patientModelList = patientRepo.findPatientModelsByEmail(email);
        if (patientModelList.size() > 0) {
            for (PatientModel model : patientModelList) {
                boolean bFullname = !Objects.equals(model.getFullname(), fullname);
                boolean bPhone = !Objects.equals(model.getPhone(), phone);
                boolean bBirthDate = !Objects.equals(model.getBirthdate(), birthDate);
                boolean bAddressCode = !Objects.equals(model.getAddressCode(), addressCode);
                if (bFullname && bPhone && bBirthDate && bAddressCode) {
                    PatientModel patientModel = new PatientModel();
                    patientModel.setEmail(email);
                    patientModel.setFullname(fullname);
                    patientModel.setPhone(phone);
                    patientModel.setBirthdate(birthDate);
                    patientModel.setAddressCode(addressCode);

                    patientRepo.save(patientModel);
                }
            }
        } else {
            PatientModel patientModel = new PatientModel();
            patientModel.setEmail(email);
            patientModel.setFullname(fullname);
            patientModel.setPhone(phone);
            patientModel.setBirthdate(birthDate);
            patientModel.setAddressCode(addressCode);

            patientRepo.save(patientModel);
        }
    }

    private void createVMIfNotExists(Timestamp vaccinationDate, Integer patientId, Integer sickId, Integer vaccineId) {
        Optional<PatientModel> patientModel = patientRepo.findById(patientId);
        if (patientModel.isEmpty()) return;
        Optional<SickModel> sickModel = sickRepo.findById(sickId);
        if (sickModel.isEmpty()) return;
        Optional<VaccineModel> vaccineModel = vaccineRepo.findById(vaccineId);
        if (vaccineModel.isEmpty()) return;

        VMModel vmModel = new VMModel();
        vmModel.setVaccineModel(vaccineModel.get());
        vmModel.setPatientModel(patientModel.get());
        vmModel.setSickModel(sickModel.get());
        vmModel.setVaccinationDate(vaccinationDate);

        vmRepo.save(vmModel);
    }
}
