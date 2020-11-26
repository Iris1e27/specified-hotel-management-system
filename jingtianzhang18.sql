-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- 主机： 127.0.0.1
-- 生成日期： 2020-06-05 12:46:49
-- 服务器版本： 10.4.11-MariaDB
-- PHP 版本： 7.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `jingtianzhang18`
--

-- --------------------------------------------------------

--
-- 表的结构 `chef`
--

CREATE TABLE `chef` (
  `chef_id` int(11) NOT NULL,
  `realname` varchar(20) NOT NULL,
  `dishes` varchar(200) NOT NULL,
  `workday` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `chef`
--

INSERT INTO `chef` (`chef_id`, `realname`, `dishes`, `workday`) VALUES
(1, 'Karen Adam', 'shrimp soup, cauliflower and mushroom stew, spicy chicken nuggets, steamed cod fish, turkey burger, veggie burger, fried egg', 'Monday, Tuesday, Wednesday, Thursday, Friday'),
(2, 'Hari Philip', 'chicken curry, chicken masala, mutton Korma, keema curry, mushroom tikka, fried egg, curry rice', 'Wednesday, Thursday, Friday, Saturday, Sunday'),
(3, 'Thalia Hensley', 'tofu teriyaki, shrimp tempura, yaki udon, chicken katsu, salmon sashimi, fried egg, curry rice', 'Monday, Thursday, Saturday'),
(4, 'Nisha Moss', 'black pepper beef, pork chowmein, sweet & sour pork, gongbao chicken, pork jiaozi, soy glazed pork chops, curry rice', 'Tuesday, Saturday, Sunday');

-- --------------------------------------------------------

--
-- 表的结构 `food_record`
--

CREATE TABLE `food_record` (
  `food_record_id` int(11) NOT NULL,
  `guest_id` int(11) NOT NULL,
  `chef_id_list` varchar(100) NOT NULL,
  `dishes` varchar(200) NOT NULL,
  `workday` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `food_record`
--

INSERT INTO `food_record` (`food_record_id`, `guest_id`, `chef_id_list`, `dishes`, `workday`) VALUES
(4, 1, '1', 'shrimp soup', 'Wednesday');

-- --------------------------------------------------------

--
-- 表的结构 `guest`
--

CREATE TABLE `guest` (
  `guest_id` int(11) NOT NULL,
  `username` varchar(20) NOT NULL,
  `realname` varchar(20) NOT NULL,
  `passport_id` varchar(20) NOT NULL,
  `tele` varchar(20) NOT NULL,
  `email` varchar(20) NOT NULL,
  `login` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `guest`
--

INSERT INTO `guest` (`guest_id`, `username`, `realname`, `passport_id`, `tele`, `email`, `login`) VALUES
(1, 'u001', 'User1', 'XXXXXX', '123456', 'u001@126.com', '001'),
(2, 'u002', 'User2', 'XXXXXX', '123456', 'u002@126.com', '002'),
(3, 'u003', 'User3', 'XXXXXX', '123456', 'u003@126.com', '003');

-- --------------------------------------------------------

--
-- 表的结构 `room`
--

CREATE TABLE `room` (
  `room_id` varchar(3) NOT NULL,
  `room_type` varchar(100) NOT NULL,
  `type_int` int(11) NOT NULL,
  `location` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `room`
--

INSERT INTO `room` (`room_id`, `room_type`, `type_int`, `location`) VALUES
('X01', 'Large room with double beds', 1, 'the northwest corner of Stairs&Looby'),
('X02', 'Large room with double beds', 1, 'the north of Stairs&Looby'),
('X03', 'Large room with a large single bed', 2, 'the north of Stairs&Looby'),
('X04', 'Large room with a large single bed', 2, 'the north of Stairs&Looby'),
('X05', 'Small room with a single bed', 3, 'the northeast corner of Stairs&Looby'),
('X06', 'Small room with a single bed', 3, 'the east of Stairs&Looby'),
('X07', 'Small room with a single bed', 3, 'the east of Stairs&Looby'),
('X08', 'Small room with a single bed', 3, 'the southeast corner of Stairs&Looby'),
('X09', 'Large room with a large single bed', 2, 'the south of Stairs&Looby'),
('X10', 'Large room with a large single bed', 2, 'the south of Stairs&Looby'),
('X11', 'Large room with double beds', 1, 'the sorth of Stairs&Looby'),
('X12', 'Large room with double beds', 1, 'the sorthwest corner of Stairs&Looby'),
('X13', 'VIP room', 4, 'the west of Stairs&Looby');

-- --------------------------------------------------------

--
-- 表的结构 `room_record`
--

CREATE TABLE `room_record` (
  `room_record_id` int(11) NOT NULL,
  `guest_id` int(11) NOT NULL,
  `room_id` varchar(3) NOT NULL,
  `room_type_int` int(11) NOT NULL,
  `checkin` date NOT NULL,
  `checkout` date NOT NULL,
  `state` int(2) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `room_record`
--

INSERT INTO `room_record` (`room_record_id`, `guest_id`, `room_id`, `room_type_int`, `checkin`, `checkout`, `state`) VALUES
(1, 1, 'X01', 1, '2020-06-01', '2020-06-02', 0),
(2, 2, 'X04', 2, '2020-06-02', '2020-06-08', 1),
(3, 1, 'X04', 2, '2020-06-01', '2020-06-02', 0),
(6, 2, 'X02', 1, '2020-06-05', '2020-06-07', 1),
(9, 2, 'X05', 3, '2020-06-08', '2020-06-10', 1),
(11, 1, 'X13', 4, '2020-06-06', '2020-06-30', 1);

-- --------------------------------------------------------

--
-- 表的结构 `staff`
--

CREATE TABLE `staff` (
  `staff_id` int(11) NOT NULL,
  `username` varchar(20) NOT NULL,
  `tele` varchar(20) NOT NULL,
  `login` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `staff`
--

INSERT INTO `staff` (`staff_id`, `username`, `tele`, `login`) VALUES
(1, 'admin', '123456', '000');

--
-- 转储表的索引
--

--
-- 表的索引 `chef`
--
ALTER TABLE `chef`
  ADD PRIMARY KEY (`chef_id`);

--
-- 表的索引 `food_record`
--
ALTER TABLE `food_record`
  ADD PRIMARY KEY (`food_record_id`),
  ADD KEY `guest_id` (`guest_id`);

--
-- 表的索引 `guest`
--
ALTER TABLE `guest`
  ADD PRIMARY KEY (`guest_id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- 表的索引 `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`room_id`);

--
-- 表的索引 `room_record`
--
ALTER TABLE `room_record`
  ADD PRIMARY KEY (`room_record_id`),
  ADD KEY `guest_id` (`guest_id`),
  ADD KEY `room_id` (`room_id`);

--
-- 表的索引 `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`staff_id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `chef`
--
ALTER TABLE `chef`
  MODIFY `chef_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- 使用表AUTO_INCREMENT `food_record`
--
ALTER TABLE `food_record`
  MODIFY `food_record_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- 使用表AUTO_INCREMENT `guest`
--
ALTER TABLE `guest`
  MODIFY `guest_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- 使用表AUTO_INCREMENT `room_record`
--
ALTER TABLE `room_record`
  MODIFY `room_record_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- 使用表AUTO_INCREMENT `staff`
--
ALTER TABLE `staff`
  MODIFY `staff_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- 限制导出的表
--

--
-- 限制表 `food_record`
--
ALTER TABLE `food_record`
  ADD CONSTRAINT `food_record_ibfk_1` FOREIGN KEY (`guest_id`) REFERENCES `guest` (`guest_id`);

--
-- 限制表 `room_record`
--
ALTER TABLE `room_record`
  ADD CONSTRAINT `room_record_ibfk_1` FOREIGN KEY (`guest_id`) REFERENCES `guest` (`guest_id`),
  ADD CONSTRAINT `room_record_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
