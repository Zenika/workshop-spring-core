<!DOCTYPE html>
<#import "/spring.ftl" as spring />
<html>
<head>
    <title>Hello Spring Boot</title>

    <link rel="stylesheet" href="<@spring.url '/styles/bootstrap/3.3.5/css/bootstrap.min.css' />" />
    <link rel="stylesheet" href="<@spring.url '/styles/bootstrap/3.3.5/css/bootstrap-theme.min.css' />" />
</head>
<body>

    <div class="container">

        <div class="row">
            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <a class="navbar-brand" href="#">Spring Boot</a>
                    </div>

                </div>
            </nav>
        </div>

        <div class="row">
            <h1>${message}</h1>

            <table class="table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Login</th>
                        <th>Password</th>
                    </tr>
                </thead>
                <tbody>
                    <#list users as user>
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.login}</td>
                        <td>${user.password}</td>
                    </tr>
                    </#list>
                </tbody>
            </table>

        </div>

    </div>





</body>
</html>