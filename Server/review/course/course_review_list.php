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

$sql = "SELECT user.user_name, user.user_image, course_review.review_rate, course_review.review_content, course_review.review_reg_date FROM course_review"
		." INNER JOIN user ON course_review.user_idx = user.user_idx WHERE course_review.course_idx = $_GET[course_idx] ORDER BY course_review.review_reg_date DESC";

if(!($query = mysql_query($sql, $con))) {
	die('Error during Query : ' .mysql_error());
	$result->result = "false";
} else {
	$result->result = "true";
	while($row = mysql_fetch_array($query, MYSQL_ASSOC)) {
		$review_list[] = $row;
	}
	$result->review_list = $review_list;
}

echo json_encode($result , JSON_UNESCAPED_UNICODE);

mysql_close($con); 
?>