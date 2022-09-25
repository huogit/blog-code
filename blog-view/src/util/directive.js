import Vue from 'vue'

/**
 * 自定义指令：
 * inserted：钩子函数  会在当前的dom元素插入到节点之后执行
 * directives 选项完成了指令的局部注册，Vue 的局部就是全局
 *    参数：
 *       el：指令绑定到的元素。这可以用于直接操作 DOM。
 *       binding：一个对象，包含以下属性。一般从这里取传过来的参数
 */



/**
 * 防抖 单位时间只触发最后一次
 * 例：<el-button v-debounce="[reset,`click`,300]">刷新</el-button>
 * 简写：<el-button v-debounce="[reset]">刷新</el-button>
 */
Vue.directive('debounce', {
    inserted: function (el, binding) {
        let [fn, event = "click", time = 300] = binding.value
        let timer
        el.addEventListener(event, () => {
            timer && clearTimeout(timer)
            timer = setTimeout(() => fn(), time)
        })
    }
})

/**
 * 节流 每单位时间可触发一次
 * 例：<el-button v-throttle="[reset,`click`,300]">刷新</el-button>
 * 传递参数：<el-button v-throttle="[()=>reset(param),`click`,300]">刷新</el-button>
 */
Vue.directive('throttle', { //throttle 指令名，function：触发是执行的逻辑
    inserted: function (el, binding) {
        //接收参数：function，event，time
        let [fn, event = "click", time = 300] = binding.value
        let now, preTime
        //addEventListener(event, function, useCapture) 方法用于向指定元素添加事件;
        //event	必须。字符串，指定事件名。function	必须。指定要事件触发时执行的函数。
        el.addEventListener(event, () => {
            //当前时间
            now = new Date()
            // 没有之前时间，或时间相关大于指定时间
            if (!preTime || now - preTime > time) {
                preTime = now
                //执行方法
                fn()
            }
        })
    }
})
