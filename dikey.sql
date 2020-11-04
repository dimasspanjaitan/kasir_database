-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Nov 02, 2020 at 02:09 PM
-- Server version: 5.7.31
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dikey`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`) VALUES
(1, 'jack', 'jack123'),
(4, 'max', 'max123');

-- --------------------------------------------------------

--
-- Table structure for table `makanan`
--

DROP TABLE IF EXISTS `makanan`;
CREATE TABLE IF NOT EXISTS `makanan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(40) NOT NULL,
  `harga` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `makanan`
--

INSERT INTO `makanan` (`id`, `nama`, `harga`) VALUES
(1, 'Mie Goreng Seafood', 18000),
(2, 'Ifu Mie', 15000),
(3, 'Batagor', 8000),
(4, 'Kwetiaw Goreng', 13000),
(5, 'Beef Steak', 45000),
(6, 'Kentang Goreng', 12000),
(7, 'Sate Ayam', 14000),
(8, 'Bakpao Ayam', 9000),
(9, 'Gado-gado', 12000),
(10, 'Babi Guling', 25000),
(11, 'Ayam Golek', 35000),
(12, 'Bakso', 16000),
(13, 'Seblak Komplit', 18000),
(14, 'Bakmi', 22000),
(15, 'Pempek', 12000),
(16, 'Soto', 12000),
(17, 'Cireng', 14000),
(18, 'Otak-otak', 3500),
(19, 'Capcai', 18000),
(20, 'Ikan Bakar', 18000),
(24, 'Sayur Asem', 15000);

-- --------------------------------------------------------

--
-- Table structure for table `minuman`
--

DROP TABLE IF EXISTS `minuman`;
CREATE TABLE IF NOT EXISTS `minuman` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(40) NOT NULL,
  `harga` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `minuman`
--

INSERT INTO `minuman` (`id`, `nama`, `harga`) VALUES
(1, 'Dalgona Coffie', 25000),
(2, 'Boba (Bubble Tea)', 45000),
(3, 'Thai Tea', 25000),
(4, 'Es Kopi Susu', 18000),
(5, 'Alpukat Kocok', 22000),
(6, 'Smoothie Manggo', 22000),
(7, 'Matcha Ice', 25000),
(8, 'Milkshake', 10000),
(9, 'Jus Tomat', 12000),
(10, 'Jus Mangga', 12000),
(11, 'Jus Wortel', 12000),
(14, 'Jus Nenas', 12000),
(13, 'Jus Buah Naga', 12000),
(17, 'Tea Bag', 8000);

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

DROP TABLE IF EXISTS `transaksi`;
CREATE TABLE IF NOT EXISTS `transaksi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kode` varchar(255) NOT NULL,
  `jumlah_bayar` int(11) NOT NULL,
  `pajak` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `kode` (`kode`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id`, `kode`, `jumlah_bayar`, `pajak`) VALUES
(1, '20201029092242', 50000, 900),
(2, '20201029092526', 100000, 2400),
(3, '20201029092833', 100000, 2400),
(4, '20201029093357', 100000, 2400);

-- --------------------------------------------------------

--
-- Table structure for table `transaksi_detail`
--

DROP TABLE IF EXISTS `transaksi_detail`;
CREATE TABLE IF NOT EXISTS `transaksi_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `transaksi_id` int(11) NOT NULL,
  `menu` varchar(40) NOT NULL,
  `harga` int(11) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `sub_total` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `transaksi_id` (`transaksi_id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaksi_detail`
--

INSERT INTO `transaksi_detail` (`id`, `transaksi_id`, `menu`, `harga`, `jumlah`, `sub_total`) VALUES
(1, 1, 'Mie Goreng Seafood', 18000, 1, 18000),
(2, 1, 'Jus Nenas', 12000, 1, 12000),
(3, 2, 'Bakmi', 22000, 2, 44000),
(4, 2, 'Jus Buah Naga', 12000, 2, 24000),
(5, 2, 'Kentang Goreng', 12000, 1, 12000),
(6, 3, 'Bakmi', 22000, 2, 44000),
(7, 3, 'Jus Buah Naga', 12000, 2, 24000),
(8, 3, 'Kentang Goreng', 12000, 1, 12000),
(9, 4, 'Bakmi', 22000, 2, 44000),
(10, 4, 'Jus Buah Naga', 12000, 2, 24000),
(11, 4, 'Kentang Goreng', 12000, 1, 12000);

-- --------------------------------------------------------

--
-- Stand-in structure for view `transaksi_detail_view`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `transaksi_detail_view`;
CREATE TABLE IF NOT EXISTS `transaksi_detail_view` (
`id` int(11)
,`transaksi_id` int(11)
,`menu` varchar(40)
,`harga` int(11)
,`jumlah` int(11)
,`sub_total` double
,`kode` varchar(255)
,`trx_id` int(11)
,`jumlah_bayar` int(11)
,`pajak` int(11)
);

-- --------------------------------------------------------

--
-- Table structure for table `transaksi_sementara`
--

DROP TABLE IF EXISTS `transaksi_sementara`;
CREATE TABLE IF NOT EXISTS `transaksi_sementara` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(40) NOT NULL,
  `harga` int(11) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `sub_total` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Stand-in structure for view `transaksi_view`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `transaksi_view`;
CREATE TABLE IF NOT EXISTS `transaksi_view` (
`id` int(11)
,`kode` varchar(255)
,`jumlah_bayar` int(11)
,`pajak` int(11)
,`total` double
);

-- --------------------------------------------------------

--
-- Structure for view `transaksi_detail_view`
--
DROP TABLE IF EXISTS `transaksi_detail_view`;

DROP VIEW IF EXISTS `transaksi_detail_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `transaksi_detail_view`  AS  select `transaksi_detail`.`id` AS `id`,`transaksi_detail`.`transaksi_id` AS `transaksi_id`,`transaksi_detail`.`menu` AS `menu`,`transaksi_detail`.`harga` AS `harga`,`transaksi_detail`.`jumlah` AS `jumlah`,`transaksi_detail`.`sub_total` AS `sub_total`,`transaksi`.`kode` AS `kode`,`transaksi`.`id` AS `trx_id`,`transaksi`.`jumlah_bayar` AS `jumlah_bayar`,`transaksi`.`pajak` AS `pajak` from (`transaksi_detail` join `transaksi` on((`transaksi`.`id` = `transaksi_detail`.`transaksi_id`))) ;

-- --------------------------------------------------------

--
-- Structure for view `transaksi_view`
--
DROP TABLE IF EXISTS `transaksi_view`;

DROP VIEW IF EXISTS `transaksi_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `transaksi_view`  AS  select `transaksi`.`id` AS `id`,`transaksi`.`kode` AS `kode`,`transaksi`.`jumlah_bayar` AS `jumlah_bayar`,`transaksi`.`pajak` AS `pajak`,(select (sum(`transaksi_detail`.`sub_total`) + `transaksi`.`pajak`) from `transaksi_detail` where (`transaksi`.`id` = `transaksi_detail`.`transaksi_id`)) AS `total` from `transaksi` ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
