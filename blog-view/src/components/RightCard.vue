<template>
  <!-- 右边卡片 -->
  <div class="card">
    <p style="margin-top: 0px;">网站公告</p>
    <el-divider></el-divider>
    <p style="margin-top: 0px;">Welcome to 别亭の Blog</p>
    <div>
      <p style="margin-left: 20px; margin-top:60px;margin-bottom:0px;font-size: 16px;font-family: 宋体;font-weight: bold;">
        我们一日日度过的所谓的日常，</p>
      <p style="margin-left: 20px; margin-top:5px;font-size:16px;font-family: 宋体;font-weight: bold;">实际上可能是接连不断的奇迹。</p>
    </div>
    <div style=" margin-top:60px;">
      <p id="showsectime">
        无
      </p>
    </div>


  </div>


</template>

<script>
export default {
  name: "RightCard",
  data() {
    return {
      runTime: 0,

    }
  },
  methods: {
    NewDate(str) {
      //Split函数是一个用于分割字符串的函数
      str = str.split('-');
      var date = new Date();
      //setUTCFullYear() 方法用于根据世界时 (UTC) 设置年份。
      date.setUTCFullYear(str[0], str[1] - 1, str[2]);
      //setUTCHours() 方法用于根据世界时 (UTC) 设置小时（0 - 23）。
      date.setUTCHours(0, 0, 0, 0);
      return date;
    },
    getRunTime() {
      //网站生日
      var birthDay = this.NewDate("2022-09-10");
      var today = new Date();
      //时间戳相减（毫秒）
      var timeold = today.getTime() - birthDay.getTime();
      
      //var sectimeold = timeold / 1000
      //向下取整
      //var secondsold = Math.floor(sectimeold);

      //一天的时间戳
      var msPerDay = 24 * 60 * 60 * 1000;
      //天数
      var e_daysold = timeold / msPerDay;
      var daysold = Math.floor(e_daysold);
      //小时
      var e_hrsold = (daysold - e_daysold) * -24;
      var hrsold = Math.floor(e_hrsold);
      //分钟
      var e_minsold = (hrsold - e_hrsold) * -60;
      var minsold = Math.floor((hrsold - e_hrsold) * -60);
      //秒
      var seconds = Math.floor((minsold - e_minsold) * -60).toString();
      //innerHTML 属性设置或返回表格行的开始和结束标签之间的 HTML。
      document.getElementById("showsectime").innerHTML = "网站已正常运行" + daysold + "天";

    }

  },
  mounted() {
    this.getRunTime();

  }
}
</script>

<style scoped>
.card {
  width: 250px;
  height: 300px;
  padding: 20px 0px 0px 0px;
  background-color: white;



  text-align: center;


}

.card:hover {
  -webkit-box-shadow: #ccc 0px 10px 10px;
  -moz-box-shadow: #ccc 0px 10px 10px;
  box-shadow: #ccc 0px 10px 10px;
}


</style>