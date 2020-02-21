<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>管理员登录</title>
    <script src="../static/js/jquery-3.3.1.js" type="text/javascript"></script>
    <script src="../static/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <style>
        .row {
            margin-top: 100px;
        }
    </style>
</head>

<body>
    <div class="container">
        <div class="row">
                <div class="col-md-4 col-md-offset-4">
                    <h2>Hautbbs Manage System</h2>
                </div>
            <div class="col-md-6 col-md-offset-3">
                <form action="/admin/login.do" method="post" >
                    <div class="form-group">
                        <label for="exampleInputEmail1">Account</label>
                        <input type="text" name="account" class="form-control" id="exampleInputEmail1" placeholder="Account">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Password</label>
                        <input type="password" name="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>

            </div>
        </div>
    </div>
</body>

</html>