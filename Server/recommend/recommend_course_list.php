<?php 
header("Content-Type:text/html;charset=utf-8");

$con = mysql_connect("localhost", "rkskekfk2020", "Tjdgkr23!");

if(!$con) {
	die('Could not connect:' .mysql_error());
}

mysql_select_db("rkskekfk2020_jeju", $con);
mysql_query("set names utf-8");
mysql_query("set session character_set_connection=utf8;");
mysql_query("set session character_set_results=utf8;");
mysql_query("set session character_set_client=utf8;");

$sql = "SELECT COUNT(reservation.course_idx), course.course_idx, course.course_name, course.course_image, course.course_price_min FROM course"
		." INNER JOIN course_type ON course_type.course_idx=course.course_idx"
		." INNER JOIN reservation ON reservation.course_idx=course.course_idx"
		." INNER JOIN course_date ON course_date.course_idx = course.course_idx"
		." WHERE course_type.course_type = $_GET[type] AND course_date.course_date BETWEEN '$_GET[start_date]' AND '$_GET[end_date]'"
		." GROUP BY reservation.course_idx ORDER BY COUNT(reservation.course_idx) DESC LIMIT 0, 10";

if(!($query = mysql_query($sql, $con))) {
	die('Error during Query : ' .mysql_error());
	$result->result = "false";
} else {
	$result->result = "true";
	while($row = mysql_fetch_array($query, MYSQL_ASSOC)) {
		$course_list[] = $row;
	}
	$result->course_list = $course_list;
}

echo json_encode($result , JSON_UNESCAPED_UNICODE);

mysql_close($con); 
?>