-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Nov 19, 2024 at 08:09 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `music`
--

-- --------------------------------------------------------

--
-- Table structure for table `songs`
--

CREATE TABLE `songs` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `artist` varchar(255) NOT NULL,
  `songPath` varchar(255) NOT NULL,
  `thumbnailPath` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `songs`
--

INSERT INTO `songs` (`id`, `title`, `artist`, `songPath`, `thumbnailPath`) VALUES
(1, 'Satu Bulan', 'Bernadya', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/music/1.wav', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/images/1.png'),
(2, 'Asing', 'Juicy Luicy', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/music/2.wav', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/images/2.png'),
(3, 'Die With a Smile', 'Lady Gaga, Bruno Mars', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/music/3.wav', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/images/3.png'),
(4, 'Kita Ke Sana', 'Hindia', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/music/4.wav', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/images/4.png'),
(5, 'Birds of A Feather', 'Billie Elish', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/music/5.wav', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/images/5.png'),
(7, 'All Too Well', 'Taylor Swift', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/music/AllTooWell.wav', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/images/6.png'),
(8, 'Untitled', 'Rex Orange Country', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/music/Untitled.wav', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/images/4seasons.jpeg'),
(9, 'Always', 'Rex Orange Country', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/music/Always.wav', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/images/always.jpeg'),
(10, '4 Seasons', 'Rex Orange Country', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/music/4Seasons.wav', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/images/4seasons.jpeg'),
(11, 'Breakeven', 'The Script', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/music/Breakeven.wav', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/images/breakeven.jpeg'),
(12, 'On Bended Knee', 'Boyz II Men', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/music/OnBendedKnee.wav', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/images/onbended.jpg'),
(13, 'Untitled', ' maliq & d’essentials', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/music/Untitledmaliq.wav', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/images/untitledmaliq.jpeg'),
(14, 'Disenchanted', 'My Chemical Romance', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/music/Disenchanted.wav', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/images/disenchanted.jpeg'),
(15, 'Thinking Out Loud', 'Ed Sheeran', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/music/ThinkingOutLoud.wav', '/Users/raflikardiansyah/Documents/Semester 5/PBO/ hehe/modul-4-jurnal-Rafli280803/res/images/thinking.jpg');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `songs`
--
ALTER TABLE `songs`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `songs`
--
ALTER TABLE `songs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
