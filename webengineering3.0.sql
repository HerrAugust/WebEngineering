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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

-- Dumping data for table webengineering.book: ~3 rows (approximately)
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT IGNORE INTO `book` (`id`, `author`, `title`, `volume`, `year`, `publisher`, `weblink`) VALUES
	(1, 'Pluto', 'Pluto sulla luna', '', 1999, '', ''),
	(2, 'Pippo', 'Pippo con Pluto', '2', 2000, 'Springer', 'http://www.disim.univaq.it/didattica/content.php?corso=640&pid=86&did=0'),
	(4, 'aa', 'aaa', '', 0, 'aa', ''),
	(12, 'author', 'titolo del libro', '2', 2, 'publisher', 'https://github.com');
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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

-- Dumping data for table webengineering.course: ~9 rows (approximately)
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT IGNORE INTO `course` (`id`, `code`, `notes_ita`, `name`, `SSD`, `language`, `semester`, `academic_year`, `prerequisites`, `learning_outcomes`, `learning_outcomes_ita`, `prerequisites_ita`, `assessment_method_ita`, `teaching_method_ita`, `assessment_method`, `teaching_method`, `syllabus`, `syllabus_ita`, `homepage`, `forum`, `notes`) VALUES
	(1, 'DT0123', '', 'Web Techs', 'INF-01', 'ita', 2, '2017/2018', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec consequat eros ac ipsum rutrum, quis mattis ante vulputate. Praesent nec mattis sapien. Nam dignissim, orci at malesuada consequat, lorem ligula lobortis turpis, vel vestibulum felis mi et nulla. Morbi ullamcorper dictum imperdiet. Ut condimentum justo non tellus mattis molestie. Praesent vitae aliquam dui, nec hendrerit ante. In porttitor dictum interdum. Etiam semper ante non dui bibendum pulvinar. Donec quis consequat felis. Vestibulum pharetra ipsum eget nisi interdum, a lobortis nisi elementum. Maecenas sollicitudin vitae odio sed viverra. ', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec consequat eros ac ipsum rutrum, quis mattis ante vulputate. Praesent nec mattis sapien. Nam dignissim, orci at malesuada consequat, lorem ligula lobortis turpis, vel vestibulum felis mi et nulla. Morbi ullamcorper dictum imperdiet. Ut condimentum justo non tellus mattis molestie. Praesent vitae aliquam dui, nec hendrerit ante. In porttitor dictum interdum. Etiam semper ante non dui bibendum pulvinar. Donec quis consequat felis. Vestibulum pharetra ipsum eget nisi interdum, a lobortis nisi elementum. Maecenas sollicitudin vitae odio sed viverra. ', '', '', '', '', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec consequat eros ac ipsum rutrum, quis mattis ante vulputate. Praesent nec mattis sapien. Nam dignissim, orci at malesuada consequat, lorem ligula lobortis turpis, vel vestibulum felis mi et nulla. Morbi ullamcorper dictum imperdiet. Ut condimentum justo non tellus mattis molestie. Praesent vitae aliquam dui, nec hendrerit ante. In porttitor dictum interdum. Etiam semper ante non dui bibendum pulvinar. Donec quis consequat felis. Vestibulum pharetra ipsum eget nisi interdum, a lobortis nisi elementum. Maecenas sollicitudin vitae odio sed viverra. ', 'prova TM', 'Vector spaces; affine and Euclidean spaces; tensors; matrix of a tensor; symmetric and antisymmetric tensors; inverse of a tensor; tensor product; orthonormal basis; eigenvectors and eigenvalues; symmetric tensors; positive de?niteness of a tensor; antisymmetric tensors; orthogonal tensors; polar decomposition theorem; left and right stretch tensors; principal stretches; the Cayley?Hamilton theorem; Gauss and Stokes?s theorems; applications of divergence theorem.', '', 'http://www.disim.univaq.it/didattica/content.php?corso=581&redir=no&pid=86&did=0', 'http://www.disim.univaq.it/didattica/content.php?corso=581&redir=no&pid=86&did=0', 'These are the notes'),
	(2, 'DT5321', 'esatti', 'Biology', 'BIO-01', 'eng', 1, '2017/2018', 'Famous artists, painters, sculptors and architects made Rome the centre of their activity, creating masterpieces throughout the city. In 1871 Rome became the capital of the Kingdom of Italy, which in 1946 became the Italian Republic.', 'Famous artists, painters, sculptors and architects made Rome the centre of their activity, creating masterpieces throughout the city. In 1871 Rome became the capital of the Kingdom of Italy, which in 1946 became the Italian Republic.', 'Il clima della cittÃ????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â  mantiene un regime piuttosto clemente per tutto l\'anno, con rari eccessi, caratteristica che la rende visitabile in ogni stagione. Le stagioni intermedie, benchÃ????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â© siano moderatamente piovose, sono le piÃ????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â¹ gradevoli. L\'autunno Ã????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â¨ nettamente piÃ????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â¹ caldo della primavera che, al contrario, soffre di scampoli d\'inverno. ', 'Il clima della cittÃ????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â  mantiene un regime piuttosto clemente per tutto l\'anno, con rari eccessi, caratteristica che la rende visitabile in ogni stagione. Le stagioni intermedie, benchÃ????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â© siano moderatamente piovose, sono le piÃ????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â¹ gradevoli. L\'autunno Ã????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â¨ nettamente piÃ????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â¹ caldo della primavera che, al contrario, soffre di scampoli d\'inverno. ', 'l clima della cittÃ???????????Ã??????????Ã?????????Ã????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â  mantiene un regime piuttosto clemente per tutto l\'anno, con rari eccessi, caratteristica che la rende visitabile in ogni stagione. Le stagioni intermedie, benchÃ???????????Ã??????????Ã?????????Ã????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â© siano moderatamente piovose, sono le piÃ???????????Ã??????????Ã?????????Ã????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â¹ gradevoli. L\'autunno Ã???????????Ã??????????Ã?????????Ã????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â¨ nettamente piÃ???????????Ã??????????Ã?????????Ã????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â¹ caldo della primavera che, al contrario, soffre di scampoli d\'inverno. La primavera Ã???????????Ã??????????Ã?????????Ã????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â¨ fortemente variabile: giornate soleggiate e miti si alternano continuamente a giornate piovose e fredde anche ad aprile inoltrato. ', 'l clima della cittÃ???????????Ã??????????Ã?????????Ã????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â  mantiene un regime piuttosto clemente per tutto l\'anno, con rari eccessi, caratteristica che la rende visitabile in ogni stagione. Le stagioni intermedie, benchÃ???????????Ã??????????Ã?????????Ã????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â© siano moderatamente piovose, sono le piÃ???????????Ã??????????Ã?????????Ã????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â¹ gradevoli. L\'autunno Ã???????????Ã??????????Ã?????????Ã????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â¨ nettamente piÃ???????????Ã??????????Ã?????????Ã????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â¹ caldo della primavera che, al contrario, soffre di scampoli d\'inverno. La primavera Ã???????????Ã??????????Ã?????????Ã????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â¨ fortemente variabile: giornate soleggiate e miti si alternano continuamente a giornate piovose e fredde anche ad aprile inoltrato. ', 'Famous artists, painters, sculptors and architects made Rome the centre of their activity, creating masterpieces throughout the city. In 1871 Rome became the capital of the Kingdom of Italy, which in 1946 became the Italian Republic.', 'Famous artists, painters, sculptors and architects made Rome the centre of their activity, creating masterpieces throughout the city. In 1871 Rome became the capital of the Kingdom of Italy, which in 1946 became the Italian Republic.', 'Famous artists, painters, sculptors and architects made Rome the centre of their activity, creating masterpieces throughout the city. In 1871 Rome became the capital of the Kingdom of Italy, which in 1946 became the Italian Republic.', 'l clima della cittÃ???????????Ã??????????Ã?????????Ã????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â  mantiene un regime piuttosto clemente per tutto l\'anno, con rari eccessi, caratteristica che la rende visitabile in ogni stagione. Le stagioni intermedie, benchÃ???????????Ã??????????Ã?????????Ã????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â© siano moderatamente piovose, sono le piÃ???????????Ã??????????Ã?????????Ã????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â¹ gradevoli. L\'autunno Ã???????????Ã??????????Ã?????????Ã????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â¨ nettamente piÃ???????????Ã??????????Ã?????????Ã????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â¹ caldo della primavera che, al contrario, soffre di scampoli d\'inverno. La primavera Ã???????????Ã??????????Ã?????????Ã????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â¨ fortemente variabile: giornate soleggiate e miti si alternano continuamente a giornate piovose e fredde anche ad aprile inoltrato. ', 'https://it.wikipedia.org/wiki/Roma', 'https://it.wikipedia.org/wiki/Roma', 'Famous artists, painters, sculptors and architects made Rome the centre of their activity, creating masterpieces throughout the city. In 1871 Rome became the capital of the Kingdom of Italy, which in 1946 became the Italian Republic.'),
	(3, 'DT4444', '', 'Informatics', 'INF-01', 'eng', 1, '2017/2018', '', '', '', '', '', '', '', '', '', '', '', '', ''),
	(4, 'ABC111', '', 'Web Development', 'INF-01', 'eng', 2, '2017/2018', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec consequat eros ac ipsum rutrum, quis mattis ante vulputate. Praesent nec mattis sapien. Nam dignissim, orci at malesuada consequat, lorem ligula lobortis turpis, vel vestibulum felis mi et nulla. Morbi ullamcorper dictum imperdiet. Ut condimentum justo non tellus mattis molestie. Praesent vitae aliquam dui, nec hendrerit ante. In porttitor dictum interdum. Etiam semper ante non dui bibendum pulvinar. Donec quis consequat felis. Vestibulum pharetra ipsum eget nisi interdum, a lobortis nisi elementum. Maecenas sollicitudin vitae odio sed viverra. ', '', '', '', '', '', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec consequat eros ac ipsum rutrum, quis mattis ante vulputate. Praesent nec mattis sapien. Nam dignissim, orci at malesuada consequat, lorem ligula lobortis turpis, vel vestibulum felis mi et nulla. Morbi ullamcorper dictum imperdiet. Ut condimentum justo non tellus mattis molestie. Praesent vitae aliquam dui, nec hendrerit ante. In porttitor dictum interdum. Etiam semper ante non dui bibendum pulvinar. Donec quis consequat felis. Vestibulum pharetra ipsum eget nisi interdum, a lobortis nisi elementum. Maecenas sollicitudin vitae odio sed viverra. ', '', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec consequat eros ac ipsum rutrum, quis mattis ante vulputate. Praesent nec mattis sapien. Nam dignissim, orci at malesuada consequat, lorem ligula lobortis turpis, vel vestibulum felis mi et nulla. Morbi ullamcorper dictum imperdiet. Ut condimentum justo non tellus mattis molestie. Praesent vitae aliquam dui, nec hendrerit ante. In porttitor dictum interdum. Etiam semper ante non dui bibendum pulvinar. Donec quis consequat felis. Vestibulum pharetra ipsum eget nisi interdum, a lobortis nisi elementum. Maecenas sollicitudin vitae odio sed viverra. ', '', '', '', ''),
	(5, 'EFG111', '', 'Web Engineering', 'INF-01', 'ita', 2, '2017/2018', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec consequat eros ac ipsum rutrum, quis mattis ante vulputate. Praesent nec mattis sapien. Nam dignissim, orci at malesuada consequat, lorem ligula lobortis turpis, vel vestibulum felis mi et nulla. Morbi ullamcorper dictum imperdiet. Ut condimentum justo non tellus mattis molestie. Praesent vitae aliquam dui, nec hendrerit ante. In porttitor dictum interdum. Etiam semper ante non dui bibendum pulvinar. Donec quis consequat felis. Vestibulum pharetra ipsum eget nisi interdum, a lobortis nisi elementum. Maecenas sollicitudin vitae odio sed viverra. ', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec consequat eros ac ipsum rutrum, quis mattis ante vulputate. Praesent nec mattis sapien. Nam dignissim, orci at malesuada consequat, lorem ligula lobortis turpis, vel vestibulum felis mi et nulla. Morbi ullamcorper dictum imperdiet. Ut condimentum justo non tellus mattis molestie. Praesent vitae aliquam dui, nec hendrerit ante. In porttitor dictum interdum. Etiam semper ante non dui bibendum pulvinar. Donec quis consequat felis. Vestibulum pharetra ipsum eget nisi interdum, a lobortis nisi elementum. Maecenas sollicitudin vitae odio sed viverra. ', '', '', '', '', '', '', '', '', '', '', ''),
	(6, 'FFF231', '', 'Foundations of computer science', 'INF-01', 'eng', 1, '2017/2018', '', '', '', '', '', '', '', '', '', '', '', '', ''),
	(15, 'DT0310', '', 'Laboratory Of Advanced Networks ', 'ENG-02', 'ita', 1, '2017/2018', '', 'learning_outcomes', '', '', '', '', '', '', '', '', '', '', ''),
	(16, 'DT0193', '', 'Advanced And Software Defined Networks', 'ENG-02', 'ita', 2, '2017/2018', '', '', '', 'Roma ha ospitato le Olimpiadi del 1960 e anche la prima edizione dei Giochi paralimpici. Nella Capitale si sono disputate le finali dei due campionati del mondo di calcio organizzati in Italia (1934 e 1990). La cittÃ???????????????Ã??????????????Ã?????????????Ã????????????Ã???????????Ã??????????Ã?????????Ã????????Ã???????Ã??????Ã?????Ã????Ã???Ã??Ã?Â  ha ospitato la II edizione dei campionati del mondo di atletica leggera nel 1987 e per due volte i campionati mondiali di nuoto (VII edizione nel 1994 e XIII nel 2009). ', '', '', '', '', '', '', '', '', ''),
	(17, 'DT5321', NULL, 'Biology', '', 'eng', 1, '2016/2017', NULL, NULL, NULL, NULL, NULL, NULL, 'Famous artists, painters, sculptors and architects made Rome the centre of their activity, creating masterpieces throughout the city. In 1871 Rome became the capital of the Kingdom of Italy, which in 1946 became the Italian Republic.', NULL, NULL, NULL, '', '', NULL),
	(19, 'DTem', '', 'Temporary', 'INF-01', 'ita', 1, '2017/2018', '', '', '', '', '', '', '', '', '', '', '', '', ''),
	(20, 'DT5321', 'esatti', 'Biology', 'BIO-01', '', 1, '2014/2015', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '', NULL);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;

-- Dumping structure for table webengineering.external_resource
CREATE TABLE IF NOT EXISTS `external_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `weblink` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Dumping data for table webengineering.external_resource: ~2 rows (approximately)
/*!40000 ALTER TABLE `external_resource` DISABLE KEYS */;
INSERT IGNORE INTO `external_resource` (`id`, `name`, `description`, `weblink`) VALUES
	(2, 'dsa', 'dsa', 'https://github.com'),
	(3, 'daaaa', '', '');
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
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=latin1;

-- Dumping data for table webengineering.image: ~7 rows (approximately)
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT IGNORE INTO `image` (`id`, `original_name`, `name_on_disk`, `path`, `course_id`) VALUES
	(57, 'britney.jpg', 'f7y6c7q.jpeg', 'C:/Users/agost/Documents/workspace/WebEngineering-master/web/uploads/', NULL),
	(62, 'b_1_q_0_p_0.jpg', 'v05chg0.jpeg', 'C:/Users/agost/Documents/workspace/WebEngineering-master/web/uploads/', NULL),
	(109, '2212dbb.jpeg', 'p0ofwf4.jpeg', 'C:/Users/agost/Documents/workspace/WebEngineering-master/web/uploads/', 16),
	(110, 'aiywe86.jpeg', '66g7aen.jpeg', 'C:/Users/agost/Documents/workspace/WebEngineering-master/web/uploads/', 16),
	(111, 'zfn1a6p.png', 'zf5oapu.png', 'C:/Users/agost/Documents/workspace/WebEngineering-master/web/uploads/', 16),
	(114, 'di caprio.jpg', 'fek3qj4.jpeg', 'C:/Users/agost/Documents/workspace/WebEngineering-master/web/uploads/', NULL),
	(117, '48029_ori.jpg', '1gy68nc.jpeg', 'C:/Users/agost/Documents/workspace/WebEngineering-master/web/uploads/', NULL);
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
INSERT IGNORE INTO `module` (`course_id`, `module_course_id`) VALUES
	(1, 4),
	(17, 6),
	(17, 16),
	(20, 4),
	(2, 16);
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

-- Dumping data for table webengineering.preparatory: ~4 rows (approximately)
/*!40000 ALTER TABLE `preparatory` DISABLE KEYS */;
INSERT IGNORE INTO `preparatory` (`course_id`, `requires`) VALUES
	(5, 6),
	(5, 3),
	(1, 6),
	(1, 6);
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
INSERT IGNORE INTO `same_as` (`course_id`, `same_as_course_id`) VALUES
	(1, 3);
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
INSERT IGNORE INTO `support` (`course_id`, `external_resource_id`) VALUES
	(19, 2),
	(19, 3);
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
INSERT IGNORE INTO `teach` (`course_id`, `teacher_id`) VALUES
	(1, 4),
	(1, 6),
	(15, 5),
	(16, 6),
	(17, 6),
	(5, 4),
	(2, 6),
	(19, 11);
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- Dumping data for table webengineering.teacher: ~5 rows (approximately)
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT IGNORE INTO `teacher` (`id`, `name`, `lastname`, `language`, `type`, `photo`, `email`, `password`) VALUES
	(4, 'Giuseppe', 'Della Penna', 'eng', 'admin', 114, 'a@a.a', 'AGOSTINO1'),
	(5, 'Francesco', 'Valentini', 'eng', 'teacher', 62, 'b@b.b', 'AGOSTINO1'),
	(6, 'Marta', 'Smith', 'eng', 'teacher', 57, 'c@c.c', 'AGOSTINO1'),
	(11, 'temp', 'temp', 'eng', 'teacher', NULL, 't@t.t', 'agostino1');
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

-- Dumping data for table webengineering.uses: ~3 rows (approximately)
/*!40000 ALTER TABLE `uses` DISABLE KEYS */;
INSERT IGNORE INTO `uses` (`course_id`, `book_id`) VALUES
	(2, 4),
	(19, 12),
	(19, 4);
/*!40000 ALTER TABLE `uses` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
