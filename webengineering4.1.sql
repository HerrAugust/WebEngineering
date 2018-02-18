-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.1.21-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win32
-- HeidiSQL Version:             9.5.0.5245
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for webengineering
CREATE DATABASE IF NOT EXISTS `webengineering` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `webengineering`;

-- Dumping structure for table webengineering.book
CREATE TABLE IF NOT EXISTS `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `author` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `volume` varchar(255) NOT NULL,
  `year` int(11) NOT NULL,
  `publisher` varchar(255) NOT NULL,
  `weblink` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

-- Dumping data for table webengineering.book: ~2 rows (approximately)
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` (`id`, `author`, `title`, `volume`, `year`, `publisher`, `weblink`) VALUES
	(16, 'Example author', 'Example of title', 'A', 2012, 'Example Publisher', 'https://it.wikipedia.org/wiki/Roma'),
	(17, 'Example author', 'Example of title', 'B', 2012, 'Example Publisher', 'https://it.wikipedia.org/wiki/Roma');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;

-- Dumping structure for table webengineering.course
CREATE TABLE IF NOT EXISTS `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(16) NOT NULL,
  `notes_ita` text,
  `name` varchar(255) NOT NULL,
  `SSD` varchar(16) NOT NULL DEFAULT '',
  `language` varchar(16) NOT NULL DEFAULT '',
  `semester` int(11) NOT NULL DEFAULT '1',
  `academic_year` varchar(16) NOT NULL DEFAULT '',
  `prerequisites` text,
  `learning_outcomes` text,
  `learning_outcomes_ita` text,
  `prerequisites_ita` text,
  `assessment_method_ita` text,
  `teaching_method_ita` text,
  `assessment_method` text,
  `teaching_method` text,
  `syllabus` text,
  `syllabus_ita` text,
  `homepage` varchar(255) NOT NULL DEFAULT '',
  `forum` varchar(255) NOT NULL DEFAULT '',
  `notes` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;

-- Dumping data for table webengineering.course: ~10 rows (approximately)
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` (`id`, `code`, `notes_ita`, `name`, `SSD`, `language`, `semester`, `academic_year`, `prerequisites`, `learning_outcomes`, `learning_outcomes_ita`, `prerequisites_ita`, `assessment_method_ita`, `teaching_method_ita`, `assessment_method`, `teaching_method`, `syllabus`, `syllabus_ita`, `homepage`, `forum`, `notes`) VALUES
	(21, 'DT1234', '', 'Bases of Biology', 'BIO-09', 'ita', 1, '2017/2018', 'correct', 'correct', 'corretto', '', '', '', '', '', '', '', '', '', ''),
	(22, 'DT1235', NULL, 'Molecular Biology', '', '', 1, '2017/2018', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '', NULL),
	(23, 'DT555', 'Il clima della città mantiene un regime piuttosto clemente per tutto l\'anno, con rari eccessi, caratteristica che la rende visitabile in ogni stagione. Le stagioni intermedie, benché siano moderatamente piovose, sono le più gradevoli. L\'autunno è nettamente più caldo della primavera che, al contrario, soffre di scampoli d\'inverno.', 'Web Technologies', '', 'ita', 1, '2017/2018', 'After the end of the Severan Dynasty in 235 the Empire entered into 50-year period known as the Crisis of the Third Century during which there were numerous putsches by generals who sought to secure the region of the empire they were entrusted with due to the weakness of central authority in Rome. ', 'After the end of the Severan Dynasty in 235 the Empire entered into 50-year period known as the Crisis of the Third Century during which there were numerous putsches by generals who sought to secure the region of the empire they were entrusted with due to the weakness of central authority in Rome. ', 'Il clima della città mantiene un regime piuttosto clemente per tutto l\'anno, con rari eccessi, caratteristica che la rende visitabile in ogni stagione. Le stagioni intermedie, benché siano moderatamente piovose, sono le più gradevoli. L\'autunno è nettamente più caldo della primavera che, al contrario, soffre di scampoli d\'inverno.', '', '', 'Il clima della città mantiene un regime piuttosto clemente per tutto l\'anno, con rari eccessi, caratteristica che la rende visitabile in ogni stagione. Le stagioni intermedie, benché siano moderatamente piovose, sono le più gradevoli. L\'autunno è nettamente più caldo della primavera che, al contrario, soffre di scampoli d\'inverno.', 'After the end of the Severan Dynasty in 235 the Empire entered into 50-year period known as the Crisis of the Third Century during which there were numerous putsches by generals who sought to secure the region of the empire they were entrusted with due to the weakness of central authority in Rome. ', 'After the end of the Severan Dynasty in 235 the Empire entered into 50-year period known as the Crisis of the Third Century during which there were numerous putsches by generals who sought to secure the region of the empire they were entrusted with due to the weakness of central authority in Rome. ', '', '', '', '', ''),
	(24, 'DT999', 'Il clima della città mantiene un regime piuttosto clemente per tutto l\'anno, con rari eccessi, caratteristica che la rende visitabile in ogni stagione. Le stagioni intermedie, benché siano moderatamente piovose, sono le più gradevoli. L\'autunno è nettamente più caldo della primavera che, al contrario, soffre di scampoli d\'inverno.', 'Informatics', 'INF-01', 'ita', 1, '2017/2018', 'After the end of the Severan Dynasty in 235 the Empire entered into 50-year period known as the Crisis of the Third Century during which there were numerous putsches by generals who sought to secure the region of the empire they were entrusted with due to the weakness of central authority in Rome. ', 'After the end of the Severan Dynasty in 235 the Empire entered into 50-year period known as the Crisis of the Third Century during which there were numerous putsches by generals who sought to secure the region of the empire they were entrusted with due to the weakness of central authority in Rome. ', 'Il clima della città mantiene un regime piuttosto clemente per tutto l\'anno, con rari eccessi, caratteristica che la rende visitabile in ogni stagione. Le stagioni intermedie, benché siano moderatamente piovose, sono le più gradevoli. L\'autunno è nettamente più caldo della primavera che, al contrario, soffre di scampoli d\'inverno.', 'Il clima della città mantiene un regime piuttosto clemente per tutto l\'anno, con rari eccessi, caratteristica che la rende visitabile in ogni stagione. Le stagioni intermedie, benché siano moderatamente piovose, sono le più gradevoli. L\'autunno è nettamente più caldo della primavera che, al contrario, soffre di scampoli d\'inverno.<b>dsjaodjsao</b>', 'Il clima della città mantiene un regime piuttosto clemente per tutto l\'anno, con rari eccessi, caratteristica che la rende visitabile in ogni stagione. Le stagioni intermedie, benché siano moderatamente piovose, sono le più gradevoli. L\'autunno è nettamente più caldo della primavera che, al contrario, soffre di scampoli d\'inverno.', 'Il clima della città mantiene un regime piuttosto clemente per tutto l\'anno, con rari eccessi, caratteristica che la rende visitabile in ogni stagione. Le stagioni intermedie, benché siano moderatamente piovose, sono le più gradevoli. L\'autunno è nettamente più caldo della primavera che, al contrario, soffre di scampoli d\'inverno.', 'After the end of the Severan Dynasty in 235 the Empire entered into 50-year period known as the Crisis of the Third Century during which there were numerous putsches by generals who sought to secure the region of the empire they were entrusted with due to the weakness of central authority in Rome. ', 'After the end of the Severan Dynasty in 235 the Empire entered into 50-year period known as the Crisis of the Third Century during which there were numerous putsches by generals who sought to secure the region of the empire they were entrusted with due to the weakness of central authority in Rome. ', 'After the end of the Severan Dynasty in 235 the Empire entered into 50-year period known as the Crisis of the Third Century during which there were numerous putsches by generals who sought to secure the region of the empire they were entrusted with due to the weakness of central authority in Rome. ', 'Il clima della città mantiene un regime piuttosto clemente per tutto l\'anno, con rari eccessi, caratteristica che la rende visitabile in ogni stagione. Le stagioni intermedie, benché siano moderatamente piovose, sono le più gradevoli. L\'autunno è nettamente più caldo della primavera che, al contrario, soffre di scampoli d\'inverno.', 'https://en.wikipedia.org/wiki/Rome', '', 'After the end of the Severan Dynasty in 235 the Empire entered into 50-year period known as the Crisis of the Third Century during which there were numerous putsches by generals who sought to secure the region of the empire they were entrusted with due to the weakness of central authority in Rome. '),
	(25, 'DT888', '', 'Databases', 'INF-01', 'ita', 1, '2017/2018', 'After the end of the Severan Dynasty in 235 the Empire entered into 50-year period known as the Crisis of the Third Century during which there were numerous putsches by generals who sought to secure the region of the empire they were entrusted with due to the weakness of central authority in Rome. There was the so-called Gallic Empire from 260-274 and the revolts of Zenobia and her father from the mid-260s which sought to fend off Persian incursions. Some regions - Britain, Spain and North Africa - were hardly affected. Instability caused economic deterioration, and there was a rapid rise in inflation as the government debased the currency in order to meet expenses. ', 'After the end of the Severan Dynasty in 235 the Empire entered into 50-year period known as the Crisis of the Third Century during which there were numerous putsches by generals who sought to secure the region of the empire they were entrusted with due to the weakness of central authority in Rome. There was the so-called Gallic Empire from 260-274 and the revolts of Zenobia and her father from the mid-260s which sought to fend off Persian incursions. Some regions - Britain, Spain and North Africa - were hardly affected. Instability caused economic deterioration, and there was a rapid rise in inflation as the government debased the currency in order to meet expenses. ', '', 'Il clima della città mantiene un regime piuttosto clemente per tutto l\'anno, con rari eccessi, caratteristica che la rende visitabile in ogni stagione. Le stagioni intermedie, benché siano moderatamente piovose, sono le più gradevoli. L\'autunno è nettamente più caldo della primavera che, al contrario, soffre di scampoli d\'inverno.', '', '', 'After the end of the Severan Dynasty in 235 the Empire entered into 50-year period known as the Crisis of the Third Century during which there were numerous putsches by generals who sought to secure the region of the empire they were entrusted with due to the weakness of central authority in Rome. There was the so-called Gallic Empire from 260-274 and the revolts of Zenobia and her father from the mid-260s which sought to fend off Persian incursions. Some regions - Britain, Spain and North Africa - were hardly affected. Instability caused economic deterioration, and there was a rapid rise in inflation as the government debased the currency in order to meet expenses. ', '', '', '', '', '', ''),
	(26, 'DT111', 'Il clima della città mantiene un regime piuttosto clemente per tutto l\'anno, con rari eccessi, caratteristica che la rende visitabile in ogni stagione. Le stagioni intermedie, benché siano moderatamente piovose, sono le più gradevoli. L\'autunno è nettamente più caldo della primavera che, al contrario, soffre di scampoli d\'inverno.', 'Images Processing', 'ENG-02', 'eng', 2, '2017/2018', 'Diocletian tried to turn into a system of non-dynastic succession. Upon abdication in 305 Caesars succeeded and they in turn appointed to colleagues for themselves.[38] After the abdication of Diocletian and Maximian in 305 and a series of civil wars between rival claimants to imperial power during the years 306-313, the Tetrarchy was abandoned. Constantine called the Great undertook a major reform of the bureaucracy not by changing the structure but by rationalizing the competencies of the several ministries during the years 325-330 after he defeated Licinius, emperor in the East at the end of 324.', '', 'Il clima della città mantiene un regime piuttosto clemente per tutto l\'anno, con rari eccessi, caratteristica che la rende visitabile in ogni stagione. Le stagioni intermedie, benché siano moderatamente piovose, sono le più gradevoli. L\'autunno è nettamente più caldo della primavera che, al contrario, soffre di scampoli d\'inverno.', 'Il clima della città mantiene un regime piuttosto clemente per tutto l\'anno, con rari eccessi, caratteristica che la rende visitabile in ogni stagione. Le stagioni intermedie, benché siano moderatamente piovose, sono le più gradevoli. L\'autunno è nettamente più caldo della primavera che, al contrario, soffre di scampoli d\'inverno.', 'Il clima della città mantiene un regime piuttosto clemente per tutto l\'anno, con rari eccessi, caratteristica che la rende visitabile in ogni stagione. Le stagioni intermedie, benché siano moderatamente piovose, sono le più gradevoli. L\'autunno è nettamente più caldo della primavera che, al contrario, soffre di scampoli d\'inverno.', 'Il clima della città mantiene un regime piuttosto clemente per tutto l\'anno, con rari eccessi, caratteristica che la rende visitabile in ogni stagione. Le stagioni intermedie, benché siano moderatamente piovose, sono le più gradevoli. L\'autunno è nettamente più caldo della primavera che, al contrario, soffre di scampoli d\'inverno.', 'Diocletian tried to turn into a system of non-dynastic succession. Upon abdication in 305 Caesars succeeded and they in turn appointed to colleagues for themselves.[38] After the abdication of Diocletian and Maximian in 305 and a series of civil wars between rival claimants to imperial power during the years 306-313, the Tetrarchy was abandoned. Constantine called the Great undertook a major reform of the bureaucracy not by changing the structure but by rationalizing the competencies of the several ministries during the years 325-330 after he defeated Licinius, emperor in the East at the end of 324.', '', '', 'Il clima della città mantiene un regime piuttosto clemente per tutto l\'anno, con rari eccessi, caratteristica che la rende visitabile in ogni stagione. Le stagioni intermedie, benché siano moderatamente piovose, sono le più gradevoli. L\'autunno è nettamente più caldo della primavera che, al contrario, soffre di scampoli d\'inverno.', '', 'https://en.wikipedia.org/wiki/Rome', 'Diocletian tried to turn into a system of non-dynastic succession. Upon abdication in 305 Caesars succeeded and they in turn appointed to colleagues for themselves.[38] After the abdication of Diocletian and Maximian in 305 and a series of civil wars between rival claimants to imperial power during the years 306-313, the Tetrarchy was abandoned. Constantine called the Great undertook a major reform of the bureaucracy not by changing the structure but by rationalizing the competencies of the several ministries during the years 325-330 after he defeated Licinius, emperor in the East at the end of 324.'),
	(27, 'DT222', '', 'Web Development', 'ENG-05', 'eng', 2, '2017/2018', 'Diocletian tried to turn into a system of non-dynastic succession. Upon abdication in 305 Caesars succeeded and they in turn appointed to colleagues for themselves.[38] After the abdication of Diocletian and Maximian in 305 and a series of civil wars between rival claimants to imperial power during the years 306-313, the Tetrarchy was abandoned. Constantine called the Great undertook a major reform of the bureaucracy not by changing the structure but by rationalizing the competencies of the several ministries during the years 325-330 after he defeated Licinius, emperor in the East at the end of 324.', 'Diocletian tried to turn into a system of non-dynastic succession. Upon abdication in 305 Caesars succeeded and they in turn appointed to colleagues for themselves.[38] After the abdication of Diocletian and Maximian in 305 and a series of civil wars between rival claimants to imperial power during the years 306-313, the Tetrarchy was abandoned. Constantine called the Great undertook a major reform of the bureaucracy not by changing the structure but by rationalizing the competencies of the several ministries during the years 325-330 after he defeated Licinius, emperor in the East at the end of 324.', '', '', '', '', 'Diocletian tried to turn into a system of non-dynastic succession. Upon abdication in 305 Caesars succeeded and they in turn appointed to colleagues for themselves.[38] After the abdication of Diocletian and Maximian in 305 and a series of civil wars between rival claimants to imperial power during the years 306-313, the Tetrarchy was abandoned. Constantine called the Great undertook a major reform of the bureaucracy not by changing the structure but by rationalizing the competencies of the several ministries during the years 325-330 after he defeated Licinius, emperor in the East at the end of 324.', 'Diocletian tried to turn into a system of non-dynastic succession. Upon abdication in 305 Caesars succeeded and they in turn appointed to colleagues for themselves.[38] After the abdication of Diocletian and Maximian in 305 and a series of civil wars between rival claimants to imperial power during the years 306-313, the Tetrarchy was abandoned. Constantine called the Great undertook a major reform of the bureaucracy not by changing the structure but by rationalizing the competencies of the several ministries during the years 325-330 after he defeated Licinius, emperor in the East at the end of 324.', 'Diocletian tried to turn into a system of non-dynastic succession. Upon abdication in 305 Caesars succeeded and they in turn appointed to colleagues for themselves.[38] After the abdication of Diocletian and Maximian in 305 and a series of civil wars between rival claimants to imperial power during the years 306-313, the Tetrarchy was abandoned. Constantine called the Great undertook a major reform of the bureaucracy not by changing the structure but by rationalizing the competencies of the several ministries during the years 325-330 after he defeated Licinius, emperor in the East at the end of 324.', '', 'https://en.wikipedia.org/wiki/Rome', '', 'Diocletian tried to turn into a system of non-dynastic succession. Upon abdication in 305 Caesars succeeded and they in turn appointed to colleagues for themselves.[38] After the abdication of Diocletian and Maximian in 305 and a series of civil wars between rival claimants to imperial power during the years 306-313, the Tetrarchy was abandoned. Constantine called the Great undertook a major reform of the bureaucracy not by changing the structure but by rationalizing the competencies of the several ministries during the years 325-330 after he defeated Licinius, emperor in the East at the end of 324.'),
	(28, 'DT1234', NULL, 'Biology', '', '', 2, '2014/2015', 'AAAAAAAA1', 'correct', 'corretto', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '', NULL),
	(29, 'DT1234', NULL, 'Bases of Biology', '', '', 1, '2015/2016', 'correct', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '', NULL);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;

-- Dumping structure for table webengineering.external_resource
CREATE TABLE IF NOT EXISTS `external_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `weblink` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Dumping data for table webengineering.external_resource: ~2 rows (approximately)
/*!40000 ALTER TABLE `external_resource` DISABLE KEYS */;
INSERT INTO `external_resource` (`id`, `name`, `description`, `weblink`) VALUES
	(4, 'Example of Resource', 'Example of Description', 'https://it.wikipedia.org/wiki/Roma'),
	(5, 'Example of Resource', 'Example of Description', 'https://it.wikipedia.org/wiki/Roma');
/*!40000 ALTER TABLE `external_resource` ENABLE KEYS */;

-- Dumping structure for table webengineering.image
CREATE TABLE IF NOT EXISTS `image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `original_name` varchar(255) NOT NULL,
  `name_on_disk` varchar(255) NOT NULL,
  `path` varchar(255) NOT NULL,
  `course_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `image_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=latin1;

-- Dumping data for table webengineering.image: ~6 rows (approximately)
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` (`id`, `original_name`, `name_on_disk`, `path`, `course_id`) VALUES
	(126, 'b_1_q_0_p_0.jpg', 'koa206o.jpeg', 'C:/Users/agost/Documents/workspace/WebEngineering-master/web/uploads/', NULL),
	(128, 'britney.jpg', 'ut2vvs7.jpeg', 'C:/Users/agost/Documents/workspace/WebEngineering-master/web/uploads/', NULL),
	(131, '48029_ori.jpg', 'qindv0h.jpeg', 'C:/Users/agost/Documents/workspace/WebEngineering-master/web/uploads/', NULL),
	(132, '2212dbb.jpeg', 'y5j8ecj.jpeg', 'C:/Users/agost/Documents/workspace/WebEngineering-master/web/uploads/', 26),
	(133, 'aiywe86.jpeg', 'gnph2ck.jpeg', 'C:/Users/agost/Documents/workspace/WebEngineering-master/web/uploads/', 26),
	(134, 'zfn1a6p.png', '5811ooz.png', 'C:/Users/agost/Documents/workspace/WebEngineering-master/web/uploads/', 26);
/*!40000 ALTER TABLE `image` ENABLE KEYS */;

-- Dumping structure for table webengineering.module
CREATE TABLE IF NOT EXISTS `module` (
  `course_id` int(11) NOT NULL,
  `module_course_id` int(11) NOT NULL,
  KEY `FK_module_course` (`course_id`),
  KEY `FK_module_course_2` (`module_course_id`),
  CONSTRAINT `FK_module_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  CONSTRAINT `FK_module_course_2` FOREIGN KEY (`module_course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table webengineering.module: ~1 rows (approximately)
/*!40000 ALTER TABLE `module` DISABLE KEYS */;
INSERT INTO `module` (`course_id`, `module_course_id`) VALUES
	(21, 22);
/*!40000 ALTER TABLE `module` ENABLE KEYS */;

-- Dumping structure for table webengineering.preparatory
CREATE TABLE IF NOT EXISTS `preparatory` (
  `course_id` int(11) NOT NULL,
  `requires` int(11) NOT NULL,
  KEY `course_id` (`course_id`),
  KEY `preparatory_to` (`requires`),
  CONSTRAINT `preparatory_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  CONSTRAINT `preparatory_ibfk_2` FOREIGN KEY (`requires`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table webengineering.preparatory: ~1 rows (approximately)
/*!40000 ALTER TABLE `preparatory` DISABLE KEYS */;
INSERT INTO `preparatory` (`course_id`, `requires`) VALUES
	(23, 25);
/*!40000 ALTER TABLE `preparatory` ENABLE KEYS */;

-- Dumping structure for table webengineering.same_as
CREATE TABLE IF NOT EXISTS `same_as` (
  `course_id` int(11) NOT NULL,
  `same_as_course_id` int(11) NOT NULL,
  KEY `FK_same_as_course` (`course_id`),
  KEY `FK_same_as_course_2` (`same_as_course_id`),
  CONSTRAINT `FK_same_as_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  CONSTRAINT `FK_same_as_course_2` FOREIGN KEY (`same_as_course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table webengineering.same_as: ~1 rows (approximately)
/*!40000 ALTER TABLE `same_as` DISABLE KEYS */;
INSERT INTO `same_as` (`course_id`, `same_as_course_id`) VALUES
	(23, 27);
/*!40000 ALTER TABLE `same_as` ENABLE KEYS */;

-- Dumping structure for table webengineering.support
CREATE TABLE IF NOT EXISTS `support` (
  `course_id` int(11) NOT NULL,
  `external_resource_id` int(11) NOT NULL,
  KEY `course_id` (`course_id`),
  KEY `external_resource_id` (`external_resource_id`),
  CONSTRAINT `support_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  CONSTRAINT `support_ibfk_2` FOREIGN KEY (`external_resource_id`) REFERENCES `external_resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table webengineering.support: ~2 rows (approximately)
/*!40000 ALTER TABLE `support` DISABLE KEYS */;
INSERT INTO `support` (`course_id`, `external_resource_id`) VALUES
	(25, 4),
	(25, 5);
/*!40000 ALTER TABLE `support` ENABLE KEYS */;

-- Dumping structure for table webengineering.teach
CREATE TABLE IF NOT EXISTS `teach` (
  `course_id` int(11) NOT NULL,
  `teacher_id` int(11) NOT NULL,
  KEY `course_id` (`course_id`),
  KEY `teacher_id` (`teacher_id`),
  CONSTRAINT `teach_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  CONSTRAINT `teach_ibfk_2` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table webengineering.teach: ~7 rows (approximately)
/*!40000 ALTER TABLE `teach` DISABLE KEYS */;
INSERT INTO `teach` (`course_id`, `teacher_id`) VALUES
	(23, 13),
	(24, 12),
	(25, 4),
	(27, 13),
	(22, 12),
	(21, 12),
	(26, 13);
/*!40000 ALTER TABLE `teach` ENABLE KEYS */;

-- Dumping structure for table webengineering.teacher
CREATE TABLE IF NOT EXISTS `teacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `language` varchar(16) NOT NULL,
  `type` varchar(255) NOT NULL,
  `photo` int(11) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `photo` (`photo`),
  CONSTRAINT `teacher_ibfk_1` FOREIGN KEY (`photo`) REFERENCES `image` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

-- Dumping data for table webengineering.teacher: ~3 rows (approximately)
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` (`id`, `name`, `lastname`, `language`, `type`, `photo`, `email`, `password`) VALUES
	(4, 'Antony', 'Rossi', 'ita', 'admin', 131, 'a@a.a', 'AAAAAAAA1'),
	(12, 'Marta ', 'Smith', 'eng', 'teacher', 128, 'm@m.m', 'AAAAAAAA1'),
	(13, 'Frank', 'Trump', 'ita', 'teacher', 126, 'f@f.f', 'AAAAAAAA1');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;

-- Dumping structure for table webengineering.uses
CREATE TABLE IF NOT EXISTS `uses` (
  `course_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  KEY `book_id` (`book_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `uses_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT `uses_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table webengineering.uses: ~2 rows (approximately)
/*!40000 ALTER TABLE `uses` DISABLE KEYS */;
INSERT INTO `uses` (`course_id`, `book_id`) VALUES
	(25, 16),
	(25, 17);
/*!40000 ALTER TABLE `uses` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
