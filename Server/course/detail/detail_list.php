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

$sql = "SELECT location.location_idx, location.location_name, location.location_image1, location.location_image2, location.location_total_rate,"
		." detail_course.detail_course_time, detail_course.detail_course_info, detail_course.detail_course_image"
		." FROM detail_course INNER JOIN location ON detail_course.detail_course_location_idx = location.location_idx"
		." WHERE course_idx = $_GET[course_idx] ORDER BY detail_course_order ASC";

if(!($query = mysql_query($sql, $con))) {
	die('Error during Query : ' .mysql_error());
	$result->result = "false";
} else {
	$result->result = "true";
	while($row = mysql_fetch_array($query, MYSQL_ASSOC)) {
		$detail_list[] = $row;
	}
	$result->detail_list = $detail_list;
}

echo json_encode($result , JSON_UNESCAPED_UNICODE);

mysql_close($con); 
?>