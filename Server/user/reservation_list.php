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

$sql = "SELECT reservation.reservation_idx, reservation.user_idx, reservation.course_idx, reservation.reservation_person_number,"
		." reservation.reservation_date, reservation.reservation_price, reservation.reservation_name, reservation.reservation_email, reservation.reservation_phone,"
		." reservation.reservation_request, user.user_name, course.course_image, course.course_name FROM reservation"
		." INNER JOIN user ON user.user_idx = reservation.user_idx INNER JOIN course ON course.course_idx = reservation.course_idx"
		." WHERE reservation.user_idx = $_GET[user_idx] ORDER BY reservation_idx DESC";

if(!($query = mysql_query($sql, $con))) {
	die('Error during Query : ' .mysql_error());
	$result->result = "false";
} else {
	$result->result = "true";
	while($row = mysql_fetch_array($query, MYSQL_ASSOC)) {
		$reservation_list[] = $row;
	}
	$result->reservation_list = $reservation_list;
}

echo json_encode($result , JSON_UNESCAPED_UNICODE);

mysql_close($con); 
?>