-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 28, 2021 at 02:17 AM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 7.4.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `baza_javafx`
--

-- --------------------------------------------------------

--
-- Table structure for table `blok_lista`
--

CREATE TABLE `blok_lista` (
  `ID` int(50) UNSIGNED NOT NULL,
  `Ime` varchar(255) NOT NULL,
  `Prezime` varchar(255) NOT NULL,
  `BrojTelefona` varchar(255) NOT NULL,
  `IDImenika` int(50) UNSIGNED DEFAULT NULL,
  `IDKorisnika` int(50) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `favoriti`
--

CREATE TABLE `favoriti` (
  `ID` int(50) UNSIGNED NOT NULL,
  `Ime` varchar(255) NOT NULL,
  `Prezime` varchar(255) NOT NULL,
  `BrojTelefona` varchar(255) NOT NULL,
  `IDImenika` int(50) UNSIGNED DEFAULT NULL,
  `IDKorisnika` int(50) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `imenik`
--

CREATE TABLE `imenik` (
  `ID` int(50) UNSIGNED NOT NULL,
  `Ime` varchar(255) NOT NULL,
  `Prezime` varchar(255) NOT NULL,
  `BrojTelefona` varchar(255) NOT NULL,
  `IDKorisnika` int(50) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `imenik`
--

INSERT INTO `imenik` (`ID`, `Ime`, `Prezime`, `BrojTelefona`, `IDKorisnika`) VALUES
(4, 'Ivan', 'Ivanić', '063/222-333', 1),
(5, 'Marko', 'Markić', '063 555 555', 2),
(17, 'Luka', 'Lukić', '123456', 1),
(19, 'Marko', 'Markić', '063 226 077', 1),
(23, 'Mate', 'Matić', '12356', 2),
(34, 'a', 'a', '123', 15);

-- --------------------------------------------------------

--
-- Table structure for table `korisnik`
--

CREATE TABLE `korisnik` (
  `ID` int(50) UNSIGNED NOT NULL,
  `KorisnickoIme` varchar(255) NOT NULL,
  `Lozinka` varchar(255) NOT NULL,
  `Uloga` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `korisnik`
--

INSERT INTO `korisnik` (`ID`, `KorisnickoIme`, `Lozinka`, `Uloga`) VALUES
(1, 'mstojic@sum.ba', '123456', 'Administrator'),
(2, 'foroz@sum.ba', '123456', 'Administrator'),
(3, 'apejičinović@sum.ba', '123456', 'Administrator'),
(15, 'ivan', '123456', 'Korisnik');

-- --------------------------------------------------------

--
-- Table structure for table `podaci`
--

CREATE TABLE `podaci` (
  `ID` int(50) UNSIGNED NOT NULL,
  `Ime` varchar(255) NOT NULL,
  `Prezime` varchar(255) NOT NULL,
  `BrojTelefona` varchar(255) NOT NULL,
  `Adresa` varchar(255) NOT NULL,
  `IDKorisnika` int(50) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `podaci`
--

INSERT INTO `podaci` (`ID`, `Ime`, `Prezime`, `BrojTelefona`, `Adresa`, `IDKorisnika`) VALUES
(5, 'Matej', 'Stojić', '123456', 'NekaAdresa', 1),
(7, 'Andrej', 'Pejičinović', '123 456 789', 'NekaAdresa', 3),
(10, 'Filip', 'Oroz', '063 555 555', 'NekaAdresa', 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `blok_lista`
--
ALTER TABLE `blok_lista`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `IDKorisnika` (`IDKorisnika`),
  ADD KEY `IDImenika` (`IDImenika`) USING BTREE;

--
-- Indexes for table `favoriti`
--
ALTER TABLE `favoriti`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `IDImenika` (`IDImenika`) USING BTREE,
  ADD KEY `IDKorisnika` (`IDKorisnika`);

--
-- Indexes for table `imenik`
--
ALTER TABLE `imenik`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `IDImenika` (`IDKorisnika`);

--
-- Indexes for table `korisnik`
--
ALTER TABLE `korisnik`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `podaci`
--
ALTER TABLE `podaci`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `IDKorisnika` (`IDKorisnika`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `blok_lista`
--
ALTER TABLE `blok_lista`
  MODIFY `ID` int(50) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `favoriti`
--
ALTER TABLE `favoriti`
  MODIFY `ID` int(50) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `imenik`
--
ALTER TABLE `imenik`
  MODIFY `ID` int(50) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `korisnik`
--
ALTER TABLE `korisnik`
  MODIFY `ID` int(50) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `podaci`
--
ALTER TABLE `podaci`
  MODIFY `ID` int(50) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `blok_lista`
--
ALTER TABLE `blok_lista`
  ADD CONSTRAINT `korisnikBlokListaFK` FOREIGN KEY (`IDKorisnika`) REFERENCES `korisnik` (`ID`);

--
-- Constraints for table `favoriti`
--
ALTER TABLE `favoriti`
  ADD CONSTRAINT `imenikFavoritiFK` FOREIGN KEY (`IDImenika`) REFERENCES `imenik` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `korisnikFavoritiFK` FOREIGN KEY (`IDKorisnika`) REFERENCES `korisnik` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `imenik`
--
ALTER TABLE `imenik`
  ADD CONSTRAINT `korisnikImenikFK` FOREIGN KEY (`IDKorisnika`) REFERENCES `korisnik` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `podaci`
--
ALTER TABLE `podaci`
  ADD CONSTRAINT `korisnikPodaciFK` FOREIGN KEY (`IDKorisnika`) REFERENCES `korisnik` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
