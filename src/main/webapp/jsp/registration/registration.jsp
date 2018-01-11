<html>
<head>
    <title>Registration</title>
    <link href="../../css/all-backgrounds.css" rel="stylesheet">
    <link href="../../css/registration/registration.css" rel="stylesheet">
</head>
<body>
<div id="wrapper">
    <form action="../CruiseServlet?command=registration" method="post">
        <fieldset>
            <legend>Registration</legend>
            <div>
                <input type="text" name="first_name" placeholder="First Name"/>
            </div>
            <div>
                <input type="text" name="last_name" placeholder="Last Name"/>
            </div>
            <div>
                <input type="text" name="email" placeholder="Email"/>
            </div>
            <div>
                <input type="password" name="password" placeholder="Password"/>
            </div>
            <input type="submit" name="submit" value="Send"/>
        </fieldset>
    </form>
</div>
</body>
</html>