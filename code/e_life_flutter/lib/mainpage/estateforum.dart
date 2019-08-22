import 'package:flutter/material.dart';
import '../bottom_navigation_widget.dart';
import 'package:flutter_swiper/flutter_swiper.dart';
import 'postDetail.dart';
import 'package:e_life_flutter/mainpage/estateforumhttp.dart';
import 'addPost.dart';
import 'news.dart';
import 'package:oktoast/oktoast.dart';
class tabChoice {
  const tabChoice({this.title, this.num, this.position});
  final String title;
  final int position;
  final String num;
}
class estateforum extends StatefulWidget {
  final communityId;
  final username;
  var session;
  var forumTabsList;
  estateforum(this.communityId, this.username,this.session,this.forumTabsList);
  @override
  State<StatefulWidget> createState() {
    return new estateforumWidget(communityId, username,session,forumTabsList);
  }
}
class estateforumWidget extends State<estateforum>
    with SingleTickerProviderStateMixin, NetListener {
  final communityId;
  final username;
  var session;
  var forumTabsList;
  estateforumWidget(this.communityId, this.username,this.session,this.forumTabsList);

  estateforumHttp manager = new estateforumHttp();
  List<tabChoice> tabs = []; //导航栏
  TabController mTabController ;
  int mCurrentPosition = 0;
  List<Widget> forums = [];
  List<Post> postList = [];


  Widget _getpost(
      String name, String time, String title, String pid, Post post) {
    return new SizedBox(
      //height: 210.0, //设置高度
      child: new Card(
        //elevation: 0.1, //设置阴影
//        shape: const RoundedRectangleBorder(
//            borderRadius: BorderRadius.all(Radius.circular(0.0))), //设置圆角
        child: new Column(
          // card只能有一个widget，但这个widget内容可以包含其他的widget
          children: [
            new ListTile(
              leading: new Text(name),
              title: new Text(title),
              subtitle: new Row(
                children: <Widget>[
                  new Text('时间:' + time),
                ],
              ),
              trailing: new Icon(Icons.keyboard_arrow_right),
              onTap: () {
//        Navigator.push<String>(context, new MaterialPageRoute(builder: (context) {
//          return new HeadImageUploadPage();
//        }));
                Navigator.push<String>(context,
                    new MaterialPageRoute(builder: (context) {
                      return new postDetail(pid, username, post, session);
                    }));
              },
              dense: true,
            ),
          ],
        ),
      ),
    );
  }

  // 从 itemBuilder 调用的独立函数
  Widget buildforumBody(BuildContext ctxt, int index) {
    return forums[index];
  }
  @override
  Widget build(BuildContext context) {

    forums = [];
    if (postList.length > 0) {
      print("加载帖子成功");
      for (int i = 1; i < postList.length; i++) {
//        print(postList[i].posterName);
//        print(postList[i].postTime);
//        print(postList[i].title);
        Widget post5 = _getpost(postList[i].posterName, postList[i].postTime,
            postList[i].title, postList[i].id, postList[i]);

        forums.add(post5);
      }
    }
    List<Widget> widgetList = []; //!!!要显示的下方组件
    //widgetList.add(value);
    return Scaffold(
      backgroundColor:Colors.white70,
      appBar: new TabBar(

        labelColor: Colors.blue,
        unselectedLabelColor: Colors.blueAccent,
        tabs: tabs.map((tabChoice choice) {

          return new SizedBox(
            //height: 210.0, //设置高度
            child:  new Tab(
                icon: new RaisedButton(
                  color: Colors.blueAccent,
                  highlightColor: Colors.blue[700],
                  colorBrightness: Brightness.dark,
                  splashColor: Colors.grey,
                  child: Text(choice.title),
                  shape:
                  RoundedRectangleBorder(borderRadius: BorderRadius.circular(20.0)),
                  onPressed: () async {
                    print(choice.num);
                  },

                )
            ),
          );
        }).toList(),
        controller: mTabController,
        isScrollable: true,
        indicator: const BoxDecoration(),//加了之后指示条消失
      ),
      body: new Container(
        child: new ListView.builder(
            scrollDirection: Axis.vertical,
            itemCount: forums.length,
            itemBuilder: (BuildContext ctxt, int index) =>
                buildforumBody(ctxt, index)),
      ),
      floatingActionButton: mCurrentPosition==1?RaisedButton(
        color: Colors.blue,
        highlightColor: Colors.blue[700],
        colorBrightness: Brightness.dark,
        splashColor: Colors.grey,
        child: Text("发布帖子"),
        shape:
        RoundedRectangleBorder(borderRadius: BorderRadius.circular(20.0)),
        onPressed: () async {
          Navigator.push<String>(context,
              new MaterialPageRoute(builder: (context) {
                return new addPost(username,communityId,session);
              })).then((String result) {
            print("报名收到的信息为:" + result);
            showToast(result);
            setState(() {

            });


          });
        },
      ):null,
    );
  }
  _getPost() async {
    print("aaaaaaaaa");
    await manager.getPostList(this, communityId, "1", "100", session);


    return true;
  }
  @override
  void initState() {
    super.initState();

    _getPost();
    if(forumTabsList.length>0){
      for(int i=0;i<forumTabsList.length;i++){
        print(forumTabsList[i].tag);
        tabs.add(tabChoice(title: forumTabsList[i].tag, num: forumTabsList[i].num, position: i));
      }
      mTabController = new TabController(vsync: this, length: forumTabsList.length);
      //判断TabBar是否切换
      mTabController.addListener(() {
        if (mTabController.indexIsChanging) {
          setState(() {
            mCurrentPosition = mTabController.index;
          });
        }
      });
    }


  }

  @override
  void dispose() {
    super.dispose();
    mTabController.dispose();
  }

  @override
  void onAllPostResponse(List<Post> body) {
    postList = body;

    setState(() {

    });
  }

  @override
  void onAllCommentsResponse(List<Comment> commentList) {}
  @override
  void onAddComment(bool success) {

  }
  @override
  void onDeleteComment(bool success) {}
  @override
  void onAllTabsResponse(List<forumTabs> body){

  }
  @override
  void onError(error) {}
}
