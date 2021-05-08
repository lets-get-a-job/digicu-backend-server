-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- 생성 시간: 21-05-09 01:27
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
-- 데이터베이스: `user_db`
--

-- --------------------------------------------------------

--
-- 테이블 구조 `authentication`
--

CREATE TABLE `authentication` (
  `email` varchar(320) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` varchar(5) NOT NULL,
  `expiration` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- 테이블 구조 `company_profile`
--

CREATE TABLE `company_profile` (
  `email` varchar(320) NOT NULL,
  `company_number` char(10) NOT NULL,
  `company_name` varchar(20) NOT NULL,
  `company_phone` char(11) NOT NULL,
  `company_address` varchar(255) NOT NULL,
  `company_owner` varchar(10) NOT NULL,
  `company_homepage` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `company_logo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 테이블의 덤프 데이터 `company_profile`
--

INSERT INTO `company_profile` (`email`, `company_number`, `company_name`, `company_phone`, `company_address`, `company_owner`, `company_homepage`, `company_logo`) VALUES
('formail0001@gmail.com', '1248100998', '삼성전자 주식회사', '0222550114', '수원시 영통구 삼성로 129', '이재용', 'https://samsung.com/', 'https://samsung.com/');

-- --------------------------------------------------------

--
-- 테이블 구조 `fcm_token`
--

CREATE TABLE `fcm_token` (
  `id` int NOT NULL,
  `social_id` varchar(255) DEFAULT NULL,
  `email` varchar(320) DEFAULT NULL,
  `fcm_token` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 테이블의 덤프 데이터 `fcm_token`
--

INSERT INTO `fcm_token` (`id`, `social_id`, `email`, `fcm_token`) VALUES
(2, '1234567890', NULL, 'dfkjk3243kj1lfkjskdsakjh');

-- --------------------------------------------------------

--
-- 테이블 구조 `registration`
--

CREATE TABLE `registration` (
  `email` varchar(320) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `hash_string` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `registration_date` date DEFAULT NULL,
  `type` varchar(10) NOT NULL,
  `letter_ok` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 테이블의 덤프 데이터 `registration`
--

INSERT INTO `registration` (`email`, `hash_string`, `registration_date`, `type`, `letter_ok`) VALUES
('formail0001@gmail.com', '$2b$10$L7Smm/dDvZDtU9EUGZGNuebzp2X0IBZ/nX2XsR9q.k5UWH/FZC1.u', '2021-05-07', 'company', '2020-01-31');

-- --------------------------------------------------------

--
-- 테이블 구조 `social_profile`
--

CREATE TABLE `social_profile` (
  `social_id` varchar(255) NOT NULL,
  `token` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(320) NOT NULL,
  `nickname` varchar(20) NOT NULL,
  `profile_image` varchar(255) NOT NULL,
  `thumbnail_image` varchar(255) NOT NULL,
  `registration_date` date NOT NULL,
  `letter_ok` date DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 테이블의 덤프 데이터 `social_profile`
--

INSERT INTO `social_profile` (`social_id`, `token`, `email`, `nickname`, `profile_image`, `thumbnail_image`, `registration_date`, `letter_ok`, `phone`) VALUES
('1234567890', '1234567890', 'formail0001@gmail.com', 'Hello', 'https://i.ibb.co/CnkzxPZ/Ex-UEl-F7-Vc-AMx7jx-jpeg.jpg', 'https://i.ibb.co/0QCJLZD/c828cf9773b5d9f5873b4630e3e838f719b9b1ea46425eb8823f3a03c9b6f7b2bd7678eade3ebc979e94ac538a3f1398a7d2.jpg', '2021-05-09', '2018-01-01', '01012345678');

--
-- 덤프된 테이블의 인덱스
--

--
-- 테이블의 인덱스 `authentication`
--
ALTER TABLE `authentication`
  ADD PRIMARY KEY (`email`,`type`);

--
-- 테이블의 인덱스 `company_profile`
--
ALTER TABLE `company_profile`
  ADD PRIMARY KEY (`company_number`),
  ADD KEY `email` (`email`);

--
-- 테이블의 인덱스 `fcm_token`
--
ALTER TABLE `fcm_token`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `social_id` (`social_id`),
  ADD KEY `email` (`email`);

--
-- 테이블의 인덱스 `registration`
--
ALTER TABLE `registration`
  ADD PRIMARY KEY (`email`);

--
-- 테이블의 인덱스 `social_profile`
--
ALTER TABLE `social_profile`
  ADD PRIMARY KEY (`social_id`);

--
-- 덤프된 테이블의 AUTO_INCREMENT
--

--
-- 테이블의 AUTO_INCREMENT `fcm_token`
--
ALTER TABLE `fcm_token`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- 덤프된 테이블의 제약사항
--

--
-- 테이블의 제약사항 `authentication`
--
ALTER TABLE `authentication`
  ADD CONSTRAINT `authentication_ibfk_1` FOREIGN KEY (`email`) REFERENCES `registration` (`email`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 테이블의 제약사항 `company_profile`
--
ALTER TABLE `company_profile`
  ADD CONSTRAINT `company_profile_ibfk_1` FOREIGN KEY (`email`) REFERENCES `registration` (`email`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 테이블의 제약사항 `fcm_token`
--
ALTER TABLE `fcm_token`
  ADD CONSTRAINT `fcm_token_ibfk_1` FOREIGN KEY (`email`) REFERENCES `registration` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fcm_token_ibfk_2` FOREIGN KEY (`social_id`) REFERENCES `social_profile` (`social_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
