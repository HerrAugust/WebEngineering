-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Feb 09, 2018 alle 21:41
-- Versione del server: 10.1.21-MariaDB
-- Versione PHP: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `webengineering`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `course`
--

CREATE TABLE `course` (
  `id` int(11) NOT NULL,
  `code` varchar(16) NOT NULL,
  `notes_ita` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `SSD` varchar(16) NOT NULL DEFAULT '',
  `language` varchar(16) NOT NULL DEFAULT '',
  `semester` int(11) NOT NULL DEFAULT '1',
  `academic_year` varchar(16) NOT NULL DEFAULT '',
  `prerequisites` text NOT NULL,
  `learning_outcomes` text NOT NULL,
  `learning_outcomes_ita` text NOT NULL,
  `prerequisites_ita` text NOT NULL,
  `assessment_method_ita` text NOT NULL,
  `teaching_method_ita` text NOT NULL,
  `assessment_method` text NOT NULL,
  `teaching_method` text NOT NULL,
  `syllabus` text NOT NULL,
  `syllabus_ita` text NOT NULL,
  `homepage` varchar(255) NOT NULL DEFAULT '',
  `forum` varchar(255) NOT NULL DEFAULT '',
  `notes` text NOT NULL,
  `module` int(10) DEFAULT NULL,
  `same_as` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `course`
--

INSERT INTO `course` (`id`, `code`, `notes_ita`, `name`, `SSD`, `language`, `semester`, `academic_year`, `prerequisites`, `learning_outcomes`, `learning_outcomes_ita`, `prerequisites_ita`, `assessment_method_ita`, `teaching_method_ita`, `assessment_method`, `teaching_method`, `syllabus`, `syllabus_ita`, `homepage`, `forum`, `notes`, `module`, `same_as`) VALUES
(1, 'DT0123', '', 'Web Techs', 'INF-01', 'ita', 2, '2018', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec consequat eros ac ipsum rutrum, quis mattis ante vulputate. Praesent nec mattis sapien. Nam dignissim, orci at malesuada consequat, lorem ligula lobortis turpis, vel vestibulum felis mi et nulla. Morbi ullamcorper dictum imperdiet. Ut condimentum justo non tellus mattis molestie. Praesent vitae aliquam dui, nec hendrerit ante. In porttitor dictum interdum. Etiam semper ante non dui bibendum pulvinar. Donec quis consequat felis. Vestibulum pharetra ipsum eget nisi interdum, a lobortis nisi elementum. Maecenas sollicitudin vitae odio sed viverra. ', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec consequat eros ac ipsum rutrum, quis mattis ante vulputate. Praesent nec mattis sapien. Nam dignissim, orci at malesuada consequat, lorem ligula lobortis turpis, vel vestibulum felis mi et nulla. Morbi ullamcorper dictum imperdiet. Ut condimentum justo non tellus mattis molestie. Praesent vitae aliquam dui, nec hendrerit ante. In porttitor dictum interdum. Etiam semper ante non dui bibendum pulvinar. Donec quis consequat felis. Vestibulum pharetra ipsum eget nisi interdum, a lobortis nisi elementum. Maecenas sollicitudin vitae odio sed viverra. ', '', '', '', '', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec consequat eros ac ipsum rutrum, quis mattis ante vulputate. Praesent nec mattis sapien. Nam dignissim, orci at malesuada consequat, lorem ligula lobortis turpis, vel vestibulum felis mi et nulla. Morbi ullamcorper dictum imperdiet. Ut condimentum justo non tellus mattis molestie. Praesent vitae aliquam dui, nec hendrerit ante. In porttitor dictum interdum. Etiam semper ante non dui bibendum pulvinar. Donec quis consequat felis. Vestibulum pharetra ipsum eget nisi interdum, a lobortis nisi elementum. Maecenas sollicitudin vitae odio sed viverra. ', 'prova TM', 'Vector spaces; affine and Euclidean spaces; tensors; matrix of a tensor; symmetric and antisymmetric tensors; inverse of a tensor; tensor product; orthonormal basis; eigenvectors and eigenvalues; symmetric tensors; positive de?niteness of a tensor; antisymmetric tensors; orthogonal tensors; polar decomposition theorem; left and right stretch tensors; principal stretches; the Cayley–Hamilton theorem; Gauss and Stokes’s theorems; applications of divergence theorem.', '', 'http://www.disim.univaq.it/didattica/content.php?corso=581&redir=no&pid=86&did=0', 'http://www.disim.univaq.it/didattica/content.php?corso=581&redir=no&pid=86&did=0', 'These are the notes', 3, NULL),
(2, 'DT5321', '', 'Biology', 'BIO-01', 'eng', 2, '2018', '', '', '', '', '', '', '', '', '', '', '', '', '', NULL, NULL),
(3, 'DT4444', '', 'Informatics', 'INF-01', 'eng', 1, '2017', '', '', '', '', '', '', '', '', '', '', '', '', '', NULL, NULL),
(4, 'ABC111', '', 'Web Development', 'INF-01', 'eng', 2, '', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec consequat eros ac ipsum rutrum, quis mattis ante vulputate. Praesent nec mattis sapien. Nam dignissim, orci at malesuada consequat, lorem ligula lobortis turpis, vel vestibulum felis mi et nulla. Morbi ullamcorper dictum imperdiet. Ut condimentum justo non tellus mattis molestie. Praesent vitae aliquam dui, nec hendrerit ante. In porttitor dictum interdum. Etiam semper ante non dui bibendum pulvinar. Donec quis consequat felis. Vestibulum pharetra ipsum eget nisi interdum, a lobortis nisi elementum. Maecenas sollicitudin vitae odio sed viverra. ', '', '', '', '', '', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec consequat eros ac ipsum rutrum, quis mattis ante vulputate. Praesent nec mattis sapien. Nam dignissim, orci at malesuada consequat, lorem ligula lobortis turpis, vel vestibulum felis mi et nulla. Morbi ullamcorper dictum imperdiet. Ut condimentum justo non tellus mattis molestie. Praesent vitae aliquam dui, nec hendrerit ante. In porttitor dictum interdum. Etiam semper ante non dui bibendum pulvinar. Donec quis consequat felis. Vestibulum pharetra ipsum eget nisi interdum, a lobortis nisi elementum. Maecenas sollicitudin vitae odio sed viverra. ', '', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec consequat eros ac ipsum rutrum, quis mattis ante vulputate. Praesent nec mattis sapien. Nam dignissim, orci at malesuada consequat, lorem ligula lobortis turpis, vel vestibulum felis mi et nulla. Morbi ullamcorper dictum imperdiet. Ut condimentum justo non tellus mattis molestie. Praesent vitae aliquam dui, nec hendrerit ante. In porttitor dictum interdum. Etiam semper ante non dui bibendum pulvinar. Donec quis consequat felis. Vestibulum pharetra ipsum eget nisi interdum, a lobortis nisi elementum. Maecenas sollicitudin vitae odio sed viverra. ', '', '', '', '', NULL, 1),
(5, 'EFG111', '', 'Web Engineering', 'INF-01', 'ita', 2, '', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec consequat eros ac ipsum rutrum, quis mattis ante vulputate. Praesent nec mattis sapien. Nam dignissim, orci at malesuada consequat, lorem ligula lobortis turpis, vel vestibulum felis mi et nulla. Morbi ullamcorper dictum imperdiet. Ut condimentum justo non tellus mattis molestie. Praesent vitae aliquam dui, nec hendrerit ante. In porttitor dictum interdum. Etiam semper ante non dui bibendum pulvinar. Donec quis consequat felis. Vestibulum pharetra ipsum eget nisi interdum, a lobortis nisi elementum. Maecenas sollicitudin vitae odio sed viverra. ', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec consequat eros ac ipsum rutrum, quis mattis ante vulputate. Praesent nec mattis sapien. Nam dignissim, orci at malesuada consequat, lorem ligula lobortis turpis, vel vestibulum felis mi et nulla. Morbi ullamcorper dictum imperdiet. Ut condimentum justo non tellus mattis molestie. Praesent vitae aliquam dui, nec hendrerit ante. In porttitor dictum interdum. Etiam semper ante non dui bibendum pulvinar. Donec quis consequat felis. Vestibulum pharetra ipsum eget nisi interdum, a lobortis nisi elementum. Maecenas sollicitudin vitae odio sed viverra. ', '', '', '', '', '', '', '', '', '', '', '', NULL, 1),
(6, 'FFF231', '', 'Foundations of computer science', 'INF-01', 'eng', 1, '', '', '', '', '', '', '', '', '', '', '', '', '', '', NULL, NULL),
(15, 'DT0310', '', 'Laboratory Of Advanced Networks ', 'eng-02', 'ita', 1, '', '', '', '', '', '', '', '', '', '', '', '', '', '', NULL, NULL),
(16, 'DT0193', '', 'Advanced And Software Defined Networks', 'ENG-02', 'ita', 2, '', '', '', '', 'Roma ha ospitato le Olimpiadi del 1960 e anche la prima edizione dei Giochi paralimpici. Nella Capitale si sono disputate le finali dei due campionati del mondo di calcio organizzati in Italia (1934 e 1990). La cittÃ?Â  ha ospitato la II edizione dei campionati del mondo di atletica leggera nel 1987 e per due volte i campionati mondiali di nuoto (VII edizione nel 1994 e XIII nel 2009). ', '', '', '', '', '', '', '', '', '', NULL, NULL);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`id`),
  ADD KEY `same_as` (`same_as`),
  ADD KEY `module` (`module`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `course`
--
ALTER TABLE `course`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `course`
--
ALTER TABLE `course`
  ADD CONSTRAINT `course_ibfk_2` FOREIGN KEY (`same_as`) REFERENCES `course` (`id`),
  ADD CONSTRAINT `course_ibfk_3` FOREIGN KEY (`module`) REFERENCES `course` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
