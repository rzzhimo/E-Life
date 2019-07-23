import 'package:flutter/material.dart';
import 'mainpage.dart';
import 'bottom_navigation_widget.dart';
import 'package:scoped_model/scoped_model.dart';
import 'user.dart';

void main() => runApp(new MyApp());

class MyApp extends StatelessWidget {
  //UserModel userModel = UserModel();
  @override
  Widget build(BuildContext context) {
//    return MaterialApp(
//      title: 'MainApp',
//      home: LoginWidget(),
//    );
  UserModel userModel = UserModel();
    return ScopedModel<UserModel>(
      model: userModel,
      child: new MaterialApp(
        title: 'MainApp',
        home: LoginWidget(),
      ),
    );
  }
}

class LoginWidget extends StatefulWidget {
  @override
  _Login createState() {
    return new _Login();
  }
}

class _Login extends State<LoginWidget> with SingleTickerProviderStateMixin {
  @override
  void initState() {

    super.initState();
    print(1111);
  }
  final TextEditingController _username =
      new TextEditingController.fromValue(new TextEditingValue(text: ""));
  final TextEditingController _password =
      new TextEditingController.fromValue(new TextEditingValue(text: ""));
  Widget quickLogibSession = new Container(
    width: 320.0,
    child: new Card(
      color: Colors.white,
      elevation: 16.0,
      child: new FlatButton(
        child: new Padding(
          padding: new EdgeInsets.all(10.0),
          child: new Text(
            '手机号快捷登录',
            style: new TextStyle(color: Colors.black, fontSize: 16.0),
          ),
        ),
      ),
    ),
  );
  Widget forgetSession = new Container(
    width: 320.0,
    child: new Card(
      color: Colors.white,
      elevation: 16.0,
      child: new FlatButton(
          child: new Padding(
            padding: new EdgeInsets.all(10.0),
            child: new Text(
              '忘记密码',
              style: new TextStyle(color: Colors.black, fontSize: 16.0),
            ),
          )),
    ),
  );

  String role = "0";
  @override
  Widget build(BuildContext context) {

    return  ScopedModelDescendant<UserModel>(
          builder: (context, child, model) {


            void _toNews() {
              print(_username.text);
              print(_password.text);
              print(role);
              print("aaaaaa" + model.user.toString());
              model.login(_username.text, _password.text, role);
            if (model.user != null) {
              Navigator.of(context).pushAndRemoveUntil(
                  new MaterialPageRoute(
                      builder: (context) => new BottomNavigationWidget()),
                  (route) => route == null);
            }
              if (model.user != null) {
                print("hasksa" + model.user.toString());
                Navigator.push<String>(context,
                    new MaterialPageRoute(builder: (BuildContext context) {
                      return new BottomNavigationWidget();
                    }));
              }
            }

            Widget loginSection = Container(
              width: 320.0,
              child: new Card(
                color: Colors.blue,
                elevation: 16.0,
                child: new FlatButton(
                    child: new Padding(
                      padding: new EdgeInsets.all(10.0),
                      child: new Text(
                        '登录',
                        style:
                            new TextStyle(color: Colors.white, fontSize: 16.0),
                      ),
                    ),
                    onPressed: (){

                      model.login(_username.text,_password.text,role);

                      //initState();
                      setState(() {
                         print("model"+model.user.toString());


                      });
                        Navigator.of(context).pushAndRemoveUntil(
                            new MaterialPageRoute(
                                builder: (context) => new BottomNavigationWidget()),
                                (route) => route == null);

                    }),
              ),
            );
            //print("ppppppppp" + model.user.username);
            return new MaterialApp(
                title: '小区登录',
                home: new Scaffold(
                  appBar: new AppBar(
                    title: new Text('小区登录'),
                  ),
                  body: new SingleChildScrollView(
                    child: new Column(
                      mainAxisSize: MainAxisSize.max,
                      mainAxisAlignment: MainAxisAlignment.start,
                      children: <Widget>[
                        new Padding(
                          padding: new EdgeInsets.all(30.0),
                          child: new Image.asset(
                            'images/app.png',
                            scale: 4.0,
                          ),
                        ),
                        new Padding(
                          padding:
                              new EdgeInsets.fromLTRB(20.0, 0.0, 20.0, 15.0),
                          child: new Row(
                              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                              children: [
                                new Padding(
                                  padding: new EdgeInsets.fromLTRB(
                                      0.0, 0.0, 5.0, 0.0),
                                  child: Icon(Icons.account_circle),
                                ),
                                new Expanded(
                                    child: new TextField(
                                  controller: _username,
                                  decoration: new InputDecoration(
                                    hintText: '请输入用户名',
                                  ),
                                ))
                              ]),
                        ),
                        new Padding(
                          padding:
                              new EdgeInsets.fromLTRB(20.0, 15.0, 20.0, 40.0),
                          child: new Row(
                              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                              children: [
                                new Padding(
                                  padding: new EdgeInsets.fromLTRB(
                                      0.0, 0.0, 5.0, 0.0),
                                  child: Icon(Icons.lock),
                                ),
                                new Expanded(
                                    child: new TextField(
                                  controller: _password,
                                  decoration: new InputDecoration(
                                    hintText: '请输入密码',
                                  ),
                                  obscureText: true,
                                ))
                              ]),
                        ),
                        Row(
                          children: <Widget>[
                            Flexible(
                              child: RadioListTile<String>(
                                value: '0',
                                title: Text('用户'),
                                groupValue: role,
                                onChanged: (value) {
                                  setState(() {
                                    role = value;
                                  });
                                },
                              ),
                            ),
                            Flexible(
                              child: RadioListTile<String>(
                                value: '1',
                                title: Text('管理员'),
                                groupValue: role,
                                onChanged: (value) {
                                  setState(() {
                                    role = value;
                                  });
                                },
                              ),
                            ),
                            Flexible(
                              child: RadioListTile<String>(
                                value: '2',
                                title: Text('商家'),
                                groupValue: role,
                                onChanged: (value) {
                                  setState(() {
                                    role = value;
                                  });
                                },
                              ),
                            ),
                          ],
                        ),
                        loginSection,
                        quickLogibSession,
                        forgetSession,
                        new Padding(
                          padding:
                              new EdgeInsets.fromLTRB(20.0, 15.0, 20.0, 40.0),
                        ),
                      ],
                    ),
                  ),
                ));
          },
        );
  }
}
