-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- 생성 시간: 21-05-20 14:57
-- 서버 버전: 8.0.23-0ubuntu0.20.04.1
-- PHP 버전: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 데이터베이스: `pos_db`
--

-- --------------------------------------------------------

--
-- 테이블 구조 `menu`
--

CREATE TABLE `menu` (
  `menu_id` int NOT NULL,
  `company_number` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `menu_name` varchar(20) NOT NULL,
  `menu_value` int NOT NULL,
  `regi_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 테이블의 덤프 데이터 `menu`
--

INSERT INTO `menu` (`menu_id`, `company_number`, `menu_name`, `menu_value`, `regi_date`) VALUES
(1, '1248100998', '떡볶이', 5000, '2020-03-31'),
(4, '1248100998', '순대', 10000, '2020-03-31'),
(5, '1248100998', '신라면', 5000, '2020-05-19'),
(6, '1248100998', '라면', 5000, '2020-05-19'),
(7, '1248100998', '라면', 5000, '2020-05-19');

-- --------------------------------------------------------

--
-- 테이블 구조 `payment_group`
--

CREATE TABLE `payment_group` (
  `payment_group_id` varchar(255) NOT NULL,
  `company_number` varchar(10) NOT NULL,
  `sale` int NOT NULL,
  `sum` int NOT NULL,
  `total` int NOT NULL,
  `payment_time` timestamp NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- 테이블 구조 `payment_items`
--

CREATE TABLE `payment_items` (
  `payment_id` int NOT NULL,
  `payment_group_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `menu_id` int NOT NULL,
  `payment_value` int NOT NULL,
  `payment_count` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 덤프된 테이블의 인덱스
--

--
-- 테이블의 인덱스 `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`menu_id`),
  ADD KEY `company_id` (`company_number`);

--
-- 테이블의 인덱스 `payment_group`
--
ALTER TABLE `payment_group`
  ADD PRIMARY KEY (`payment_group_id`),
  ADD KEY `company_number` (`company_number`);

--
-- 테이블의 인덱스 `payment_items`
--
ALTER TABLE `payment_items`
  ADD PRIMARY KEY (`payment_id`),
  ADD KEY `payment_group` (`payment_group_id`),
  ADD KEY `payment_items_ibfk_1` (`menu_id`);

--
-- 덤프된 테이블의 AUTO_INCREMENT
--

--
-- 테이블의 AUTO_INCREMENT `menu`
--
ALTER TABLE `menu`
  MODIFY `menu_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- 테이블의 AUTO_INCREMENT `payment_items`
--
ALTER TABLE `payment_items`
  MODIFY `payment_id` int NOT NULL AUTO_INCREMENT;

--
-- 덤프된 테이블의 제약사항
--

--
-- 테이블의 제약사항 `payment_group`
--
ALTER TABLE `payment_group`
  ADD CONSTRAINT `payment_group_ibfk_1` FOREIGN KEY (`company_number`) REFERENCES `menu` (`company_number`);

--
-- 테이블의 제약사항 `payment_items`
--
ALTER TABLE `payment_items`
  ADD CONSTRAINT `payment_items_ibfk_1` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`menu_id`),
  ADD CONSTRAINT `payment_items_ibfk_2` FOREIGN KEY (`payment_group_id`) REFERENCES `payment_group` (`payment_group_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
