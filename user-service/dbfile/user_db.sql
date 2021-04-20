-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- 생성 시간: 21-04-20 14:51
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

--
-- 테이블의 덤프 데이터 `authentication`
--

INSERT INTO `authentication` (`email`, `token`, `type`, `expiration`) VALUES
('sample@naver.com', 'ouWvPsDAqgj1cH8rXjrKGcbsoJPsP8', 'reg', NULL);

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
('sample@naver.com', '1048137225', 'sk텔레콤(주)', '0800116000', '서울 중구 을지로2가 11번지', '하성민', 'http://www.sktelecom.co.kr/', 'http://www.sktelecom.co.kr/'),
('formail0001@gmail.com', '1248100998', '(주)삼성전자', '0222550114', '수원시 영통구 삼성로 129', '이재용', 'https://samsung.net', 'https://samsung.net'),
('formail0001@naver.com', '2288802594', '(주)카카오엔터테인먼트', '07050563075', '경기도 성남시 분당구 판교역로 221 (삼평동, 투썬월드빌딩 6층)', '이진수,김성수', 'www.kakaopage.com', 'www.kakaopage.com');

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
('formail0001@gmail.com', '$2b$10$Sigvk.vG1Gxsoc5xr8h.KOcqe8oeQruwPntJkicVOtgvj0c/qdd5m', '2021-04-19', 'company', '2021-04-19'),
('formail0001@naver.com', '$2b$10$yQLsB2YgV4azpyj9S.G0UusXIH1ID5K6RgaVfv5j2cIW5eQ0iBovK', '2021-04-19', 'company', '2021-04-19'),
('sample@naver.com', '$2b$10$hlS1FQUoxirCZuu1Wq7kSuGa1wia6TB7R.fXYzJlycZb3y63KjBO.', NULL, 'company', '2021-04-19');

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
  `letter_ok` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
