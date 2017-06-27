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

$sql = "INSERT INTO course_review (user_idx, review_rate, review_content, course_idx) VALUES "
		. "($_POST[user_idx], $_POST[review_rate], '$_POST[review_content]', $_POST[course_idx])";

if(!mysql_query($sql, $con)) {
	die('Error during Query : ' .mysql_error());
	$result->result = "false";
} else {
	$update_sql = "UPDATE course SET course_total_rate = (SELECT AVG(review_rate) FROM course_review WHERE course_idx = $_POST[course_idx]),"
			." course_total_review = (SELECT COUNT(*) FROM course_review WHERE course_idx = $_POST[course_idx]) WHERE course_idx = $_POST[course_idx]";
	if(!mysql_query($update_sql, $con)) {
		die('Error during Query : ' .mysql_error());
		$result->result = "false";
	} else {
		$result->result = "true";
	}
}

echo json_encode($result , JSON_UNESCAPED_UNICODE);

mysql_close($con); 
?>