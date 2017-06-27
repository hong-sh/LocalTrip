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

$sql = "SELECT course.course_idx, user.user_idx, location.location_idx, location.location_name FROM course"
		." INNER JOIN user ON course.user_idx = user.user_idx"
		." INNER JOIN detail_course ON detail_course.course_idx = course.course_idx"
		." INNER JOIN location ON detail_course.detail_course_location_idx = location.location_idx WHERE course.course_idx = $_GET[course_idx]";

if(!($query = mysql_query($sql, $con))) {
	die('Error during Query : ' .mysql_error());
	$result->result = "false";
} else {
	$result->result = "true";
	while($row = mysql_fetch_array($query, MYSQL_ASSOC)) {
		$write_review_list[] = $row;
	}
	$result->write_review_list = $write_review_list;
}

echo json_encode($result , JSON_UNESCAPED_UNICODE);

mysql_close($con); 
?>