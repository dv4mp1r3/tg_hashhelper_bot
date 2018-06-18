Простой бот на java с имплементацией человекочитаемой обработки команд, которую непонятно зачем выпилили из используемой в проекте библиотеке, заменив на гору лямбд (HDD как он есть).

```    @Override
    public String execute() {
        String helpStr = "Пользуй меня следующим образом:\n"
                +"hashtype hash - определение алгоритма хеширования по значению\n"
                ;
        return helpStr;
    }
```

пример взаимодействия:

```
/hashtype 0800fc577294c34e0b28ad2839435945
```
пример ответа:

```
MD5
MD4
MD2
Double MD5
LM
RIPEMD-128
Haval-128
Tiger-128
Snefru-128
Skein-256(128)
Skein-512(128)
Lotus Notes/Domino 5
RAdmin v2.x
ZipMonster
md5(md5(md5($pass)))
md5(strtoupper(md5($pass)))
md5(sha1($pass))
NTLM
Domain Cached Credentials
mscash
Domain Cached Credentials 2
mscash2
DNSSEC(NSEC3)
```
для работы необходимо создать config.ini по примеру ниже

```
[main]
name=YourAwesomeBotName
token=YourAwesomeBotToken

[misc]
userId=AdminUserId (for service messages, like exceptions and other)
```