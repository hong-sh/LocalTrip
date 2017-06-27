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

$sql = "SELECT course.course_idx, course.course_name, course.course_image, course.course_price_min , course.course_total_rate, user.user_image, user.user_name FROM course"
		." INNER JOIN course_tag ON course.course_idx = course_tag.course_idx INNER JOIN course_date ON course_date.course_idx = course.course_idx"
		." INNER JOIN user ON course.user_idx = user.user_idx"
		." WHERE course_tag.course_tag_name LIKE '$_GET[tag_name]' AND course_date.course_date BETWEEN '$_GET[start_date]' AND '$_GET[end_date]' GROUP BY course.course_idx";

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