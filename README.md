<h1 align="center">Приложение для фокусировки</h1>
<h3 align="center">Приложение позволят ставить таймеры с картинками, которые пользователь поставит сам!</h3>

Проект выполнен совместно с [LeeLuiza](https://github.com/LeeLuiza). <br>

<h3 align="center">О приложении</h3>
Приложение разработано для того, чтобы помочь вам сосредоточиться на работе, а не на телефоне. Пользователи запускают таймер с определенными картинками, но если вы покините приложение до истечения срока действия таймера, то ваши минуты не сохраняться!<br> 
Все мы знаем как сложно порой бывает не отвлекаться на уведомления. Вроде зашел время посмотреть, а уже как полчаса листаешь ленту в соцсети.

С помощью нашего приложения, пользователь может:<br> 
+ фокусироваться и не отвлекаться от поставленных целей<br> 
+ просматривать сколько минут были сконцентрированы друзья<br> 
+ создавать свои индикаторы, чтобы была мотивация их запускать<br> 
+ редактировать свой статус и менять аватарку<br> 
+ просматривать количество минут своей концентрации<br>

Приложение взаимодействует с удаленном базой данной, но также присутсвует и оффайн режим.<br>

<h3 align="center">Архитектура приложения</h3>
В разработке приложения старались придерживаться чистой MVVM архитектуры, включающий в себя разделение на data, domain, presenter слои, и на di, который отвечает за их взаимодействие. <br>
<h3 align="center">Стек технологий</h3>
Список библиотек которые были использованы при разработке приложения: <br>
+ Retrofit;<br>
+ Dagger;<br>
+ View Binding Property Delegate;<br>
+ Android Navigation Component;<br>
+ Room.<br>

<h3 align="center">Демострация экранов</h3>
<h4 align="center">Регистрация</h4>
Всё начинается с фрагмента регистрации, где можно собственно зарегистрироваться, перейти к авторизации или в оффлайн режим (о нём попозже):<br> 
<div style="display: flex;" align="center"><img src="/screens/screen_1_registration.jpg" width="216" height="480" style="margin-right: 1;"></div><br>
<h4 align="center">Авторизация</h4>
В авторизации всё по классике:<br> 
<div style="display: flex;" align="center"><img src="/screens/screen_2_authorization.jpg" width="216" height="480" style="margin-right: 1;"></div><br>
<h4 align="center">История</h4>
На данном фрагменте можно увидеть историю оконченных таймеров:<br> 
<div style="display: flex;" align="center"><img src="/screens/screen_18_history.jpg" width="216" height="480" style="margin-right: 1;"></div><br>
<h4 align="center">Новый индикатор</h4>
Создадим новый индикатор, который потом будем использовать в таймере. Всего доступно 5 стадий индикатора:<br>
<div style="display: flex;" align="center"><img src="/screens/screen_3_indicator.jpg" width="216" height="480" style="margin-right: 1;"></div><br>
<h4 align="center">Список индикаторов</h4>
Все существующие индикаторы можно увидеть в разделе "Индикаторы":<br>
<div style="display: flex;" align="center"><img src="/screens/screen_4_indicators.jpg" width="216" height="480" style="margin-right: 1;"></div><br>
<div style="display: flex;" align="center"><img src="/screens/screen_3_indicator.jpg" width="216" height="480" style="margin-right: 1;"></div><br>
<h4 align="center">Отрытие индикатора</h4>
Можно открыть индикатор и увидеть его "содержимое":<br>
<div style="display: flex;" align="center"><img src="/screens/screen_17_open_indicator.jpg" width="216" height="480" style="margin-right: 1;"></div><br>
<h4 align="center">Таймер</h4>
Поставим таймер на минуту:<br>
<div style="display: flex;" align="center"><img src="/screens/screen_5_focus.jpg" width="216" height="480" style="margin-right: 1;"></div><br>
После появится новый фрагмент с запушенным таймером:
<div style="display: flex;" align="center"><img src="/screens/screen_6_new_focus_1.jpg" width="216" height="480" style="margin-right: 1;">
<img src="/screens/screen_7_new_focus_2.jpg" width="216" height="480" style="margin-right: 1;"></div><br>
Если попытаться выйти назад, то появится следующее диалоговое окно:
<div style="display: flex;" align="center"><img src="/screens/screen_19_dialog_back.jpg" width="216" height="480" style="margin-right: 1;"></div><br>
Если выйти на рабочий стол телефона или в историю открытых приложений, а потом вернуться в ещё запушенное приложение с таймером, то будет такое диалоговое окно:
<div style="display: flex;" align="center"><img src="/screens/screen_20_dialog_home.jpg" width="216" height="480" style="margin-right: 1;"></div><br>
<h4 align="center">История (возвращение)</h4>
После того, как таймер закончится успешно, информация о нём появится в истории:<br>
<div style="display: flex;" align="center"><img src="/screens/screen_8_history.jpg" width="216" height="480" style="margin-right: 1;"></div><br>
<h4 align="center">Друзья</h4>
Фрагмент, где можно увидеть друзей (сперва никого не будет):<br>
<div style="display: flex;" align="center"><img src="/screens/screen_9_friends.jpg" width="216" height="480" style="margin-right: 1;"></div><br>
Но можно найти друга по его нику:<br>
<div style="display: flex;" align="center"><img src="/screens/screen_10_find_friend.jpg" width="216" height="480" style="margin-right: 1;"></div><br>
Или просто принять чью-то заявку: <br>
<div style="display: flex;" align="center"><img src="/screens/screen_11_accept_friend.jpg" width="216" height="480" style="margin-right: 1;"></div><br>
<h4 align="center">Аккаунт</h4>
Аккаунт пользователя:<br>
<div style="display: flex;" align="center"><img src="/screens/screen_12_account.jpg" width="216" height="480" style="margin-right: 1;"></div><br>
Можно поставить статус и поменять тему: <br>
<div style="display: flex;" align="center"><img src="/screens/screen_13_set_status.jpg" width="216" height="480" style="margin-right: 1;">
<img src="/screens/screen_14_dark_theme.jpg" width="216" height="480" style="margin-right: 1;"></div><br>
Ещё можно поставить себе другую аватарку:
<div style="display: flex;" align="center"><img src="/screens/screen_16_change_avatar.jpg" width="216" height="480" style="margin-right: 1;"></div><br>
<h4 align="center">Оффлайн режим</h4>
В оффлайн режиме всё сохраняется локально на телефоне. Раздел друзья заменяется на раздел настроек:<br>
<div style="display: flex;" align="center"><img src="/screens/screen_15_offline_setting.jpg" width="216" height="480" style="margin-right: 1;"></div><br>

