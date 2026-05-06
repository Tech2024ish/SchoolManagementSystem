CREATE DATABASE IF NOT EXISTS SMS;
USE SMS;

CREATE TABLE IF NOT EXISTS Users (
  UserID INT AUTO_INCREMENT PRIMARY KEY,
  Username VARCHAR(50) UNIQUE NOT NULL,
  Password VARCHAR(255) NOT NULL,
  UserType ENUM('Admin','Teacher','Student','Parent') NOT NULL,
  ImageLink VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Courses (
  CourseId INT AUTO_INCREMENT PRIMARY KEY,
  CourseName VARCHAR(100) NOT NULL,
  Description TEXT,
  TeacherID INT
);

CREATE TABLE IF NOT EXISTS Teachers (
  TeacherID INT AUTO_INCREMENT PRIMARY KEY,
  FirstName VARCHAR(50),
  LastName VARCHAR(50),
  CourseId INT,
  Qualification VARCHAR(100),
  Experience VARCHAR(50),
  Email VARCHAR(100),
  Telephone VARCHAR(20),
  FOREIGN KEY (CourseId) REFERENCES Courses(CourseId) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS Students (
  StudentID INT AUTO_INCREMENT PRIMARY KEY,
  FirstName VARCHAR(50),
  LastName VARCHAR(50),
  RegNumber VARCHAR(20) UNIQUE,
  Telephone VARCHAR(20),
  Email VARCHAR(100),
  Address VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Parents (
  ParentId INT AUTO_INCREMENT PRIMARY KEY,
  FirstName VARCHAR(50),
  LastName VARCHAR(50),
  Email VARCHAR(100),
  Telephone VARCHAR(20),
  Location VARCHAR(255),
  StudentID INT,
  FOREIGN KEY (StudentID) REFERENCES Students(StudentID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Marks (
  MarksId INT AUTO_INCREMENT PRIMARY KEY,
  CourseId INT,
  StudentId INT,
  Marks DECIMAL(5,2),
  Grade VARCHAR(2),
  FOREIGN KEY (CourseId) REFERENCES Courses(CourseId),
  FOREIGN KEY (StudentId) REFERENCES Students(StudentID)
);

CREATE TABLE IF NOT EXISTS Announcement (
  AnnouncementId INT AUTO_INCREMENT PRIMARY KEY,
  Message TEXT,
  Date DATE,
  TargetGroup ENUM('Students','Teachers','Parents','All')
);

CREATE TABLE IF NOT EXISTS StudentBehavior (
  BehaviorId INT AUTO_INCREMENT PRIMARY KEY,
  StudentId INT,
  Behavior ENUM('Good','Misconduct','Active','Shy','Other'),
  Description TEXT,
  Date DATE,
  FOREIGN KEY (StudentId) REFERENCES Students(StudentID)
);

CREATE TABLE IF NOT EXISTS StudentTracking (
  TrackId INT AUTO_INCREMENT PRIMARY KEY,
  StudentId INT,
  Status ENUM('Wellbeing','Sick'),
  Progress VARCHAR(20),
  Location VARCHAR(20),
  Date DATE,
  FOREIGN KEY (StudentId) REFERENCES Students(StudentID)
);

CREATE TABLE IF NOT EXISTS Appointment (
  AppointmentId INT AUTO_INCREMENT PRIMARY KEY,
  Purpose TEXT,
  Responsible ENUM('Administration','Teacher'),
  Date DATE,
  RequestedBy INT,
  FOREIGN KEY (RequestedBy) REFERENCES Users(UserID)
);

CREATE TABLE IF NOT EXISTS Reports (
  ReportId INT AUTO_INCREMENT PRIMARY KEY,
  Type VARCHAR(100),
  Date DATE,
  GeneratedBy INT,
  FOREIGN KEY (GeneratedBy) REFERENCES Users(UserID)
);

CREATE TABLE IF NOT EXISTS Nurses (
  NurseId INT AUTO_INCREMENT PRIMARY KEY,
  FirstName VARCHAR(50),
  LastName VARCHAR(50),
  Telephone VARCHAR(20),
  Email VARCHAR(100),
  Address VARCHAR(255),
  HealthCenter VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS Doctors (
  DoctorId INT AUTO_INCREMENT PRIMARY KEY,
  FirstName VARCHAR(50),
  LastName VARCHAR(50),
  Telephone VARCHAR(20),
  Email VARCHAR(100),
  Address VARCHAR(255),
  HospitalName VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS Diagnosis (
  DiagnosisID INT AUTO_INCREMENT PRIMARY KEY,
  PatientID INT,
  NurseID INT,
  DoctorID INT,
  DiagnosisStatus ENUM('Pending','InProgress','Completed'),
  Result TEXT,
  Date DATE,
  FOREIGN KEY (PatientID) REFERENCES Students(StudentID) ON DELETE SET NULL,
  FOREIGN KEY (NurseID)   REFERENCES Nurses(NurseId)     ON DELETE SET NULL,
  FOREIGN KEY (DoctorID)  REFERENCES Doctors(DoctorId)   ON DELETE SET NULL
);

-- bcrypt hash below is for the literal string "password" (jbcrypt 0.4)
INSERT IGNORE INTO Users VALUES
(1,'admin','$2a$10$UMn3n6ENh9NVKqp7JaWqq.4FqCI9GRNStLaTY/c0JjC5N/LnCVKzy','Admin',NULL),
(2,'teacher1','$2a$10$UMn3n6ENh9NVKqp7JaWqq.4FqCI9GRNStLaTY/c0JjC5N/LnCVKzy','Teacher',NULL),
(3,'teacher2','$2a$10$UMn3n6ENh9NVKqp7JaWqq.4FqCI9GRNStLaTY/c0JjC5N/LnCVKzy','Teacher',NULL),
(4,'student1','$2a$10$UMn3n6ENh9NVKqp7JaWqq.4FqCI9GRNStLaTY/c0JjC5N/LnCVKzy','Student',NULL),
(5,'student2','$2a$10$UMn3n6ENh9NVKqp7JaWqq.4FqCI9GRNStLaTY/c0JjC5N/LnCVKzy','Student',NULL),
(6,'parent1','$2a$10$UMn3n6ENh9NVKqp7JaWqq.4FqCI9GRNStLaTY/c0JjC5N/LnCVKzy','Parent',NULL);

INSERT IGNORE INTO Courses VALUES
(1,'Mathematics','Core mathematics course',2),
(2,'Science','General science',2),
(3,'English','English language and literature',3);

INSERT IGNORE INTO Teachers VALUES
(1,'John','Smith',1,'BSc Mathematics','5 years','john.smith@sms.com','0780000001'),
(2,'Mary','Johnson',3,'BA English','3 years','mary.johnson@sms.com','0780000002');

INSERT IGNORE INTO Students VALUES
(1,'Alice','Brown','STU001','0780000010','alice@sms.com','123 Main St'),
(2,'Bob','Davis','STU002','0780000011','bob@sms.com','456 Oak Ave'),
(3,'Carol','Wilson','STU003','0780000012','carol@sms.com','789 Pine Rd');

INSERT IGNORE INTO Parents VALUES
(1,'James','Brown','james@email.com','0780000020','Kigali',1),
(2,'Susan','Davis','susan@email.com','0780000021','Kigali',2);

INSERT IGNORE INTO Announcement VALUES
(1,'Welcome to the new semester!','2025-01-15','All'),
(2,'Math exam next Friday','2025-01-20','Students'),
(3,'Staff meeting on Monday','2025-01-18','Teachers');

INSERT IGNORE INTO Marks VALUES
(1,1,1,85.00,'A'),(2,1,2,72.00,'B'),
(3,2,1,90.00,'A'),(4,3,3,65.00,'C');

INSERT IGNORE INTO StudentBehavior VALUES
(1,1,'Good','Participates actively in class','2025-01-15'),
(2,2,'Active','Always engaged and helpful','2025-01-15'),
(3,3,'Shy','Needs encouragement to participate','2025-01-15');

INSERT IGNORE INTO StudentTracking VALUES
(1,1,'Wellbeing','On track','In school','2025-01-15'),
(2,2,'Wellbeing','On track','In school','2025-01-15'),
(3,3,'Sick','Not good','At home','2025-01-15');

INSERT IGNORE INTO Nurses VALUES
(1,'Anna','White','0780000030','anna.white@sms.com','Kigali','SMS Health Center'),
(2,'Beth','Green','0780000031','beth.green@sms.com','Kigali','SMS Health Center');

INSERT IGNORE INTO Doctors VALUES
(1,'David','Black','0780000040','david.black@hospital.com','Kigali','King Faisal Hospital'),
(2,'Emily','Stone','0780000041','emily.stone@hospital.com','Kigali','CHUK');

INSERT IGNORE INTO Diagnosis VALUES
(1,3,1,1,'Completed','Mild flu, prescribed rest and fluids','2025-01-15'),
(2,2,2,2,'Pending','Initial consultation pending','2025-01-20');
