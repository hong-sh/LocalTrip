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


$sql = "SELECT user.user_name, user.user_image, course.course_idx, course.course_name, course.course_image, course.course_price_min, course.course_total_rate FROM course"
		." INNER JOIN wishlist ON course.course_idx=wishlist.course_idx"
		." INNER JOIN user ON course.user_idx = user.user_idx WHERE wishlist.user_idx = $_GET[user_idx] ORDER BY wishlist_idx DESC";


if(!($query = mysql_query($sql, $con))) {
	die('Error during Query : ' .mysql_error());
	$result->result = "false";
} else {
	$result->result = "true";
	while($row = mysql_fetch_array($query, MYSQL_ASSOC)) {
		$wishlist[] = $row;
	}
	$result->wishlist = $wishlist;
}

echo json_encode($result , JSON_UNESCAPED_UNICODE);

mysql_close($con); 
?>