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

$sql = "SELECT user.user_idx, user.user_name, user.user_image, guide_profile.guide_image, guide_profile.guide_introduce, guide_profile.guide_first_language, guide_profile.guide_available_language, guide_profile.guide_capability, guide_profile.guide_total_rate, guide_profile.guide_total_review, guide_profile.guide_total_course"
		." FROM user INNER JOIN guide_profile ON user.user_idx = guide_profile.user_idx WHERE user.user_idx = $_GET[user_idx]";


if(!($query = mysql_query($sql, $con))) {
	die('Error during Query : ' .mysql_error());
	$result->result = "false";
} else {
	$result->result = "true";
	$result->guide_profile = mysql_fetch_object($query);
}

echo json_encode($result , JSON_UNESCAPED_UNICODE);

mysql_close($con); 
?>