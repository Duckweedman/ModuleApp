# ModuleApp<br><br>
现流行的模块化开发封装，对混淆策略、网络请求链、配置化和路由以及分模块思路的探讨<br>
#模块化思路是：一个基础库、第三方依赖库、登录、购物车、订单、分享、支付等<br><br>
#做的一些处理（基础库，比较硬性了，主要是Rxjava和Retrofit针对页面的绑定和网络的一些判断）<br>
还有一些拓展，比如网络拦截器<br>
模仿BaseRecyclerViewAdapterHelper做了简单的针对RecyclerView的适配器<br>
针对MVP模式的统一优化<br>
加入了JsBridge，后期补上hybrid自定义策略<br>
很可惜，多模块下，ButterKnife无法再使用了（大家有好方法的可以分享下）<br><br>
#那么为什么用组件化得方式构建工程呢，现在Android独立开发的不少，一个module也没有问题吧<br>
笔者的经历：<br>
	上家公司属于技术创业型公司，Android三个人，采用的仍然是传统的开发方式，一个module一个项目，<br>
	因为以前做过类似模块化开发，很明显可以感受到区别；<br>
	比如：包机构混乱、调试困难、特别采用MVP模式，满地的present、bean、service，公司一些子项目需要开发，<br>
	发现很多功能老项目里都有，但是很凌乱。这样导致了几点问题<br>
  1：不利于代码复用<br>
  2：配置复杂<br>
  3：问题得不到集中，调试困难<br>
  4：不利于以后业务的拓展（mavens开发）<br>
	最近有项目，刚好在完善模块化方案，发现一些朋友对此也有比较高的兴趣，就打算给大家分享下，都是能看得懂的代码.<br>
	有什么不明白和优化的地方可以发邮箱：107401330@qq.com<br>
	欢迎讨论,一起进步!<br>
