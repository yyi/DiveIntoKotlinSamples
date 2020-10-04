package samples.ch02

data class Country(
        val name: String,
        val continent: String,
        val population: Int)

class CountryApp {
    fun filterCountries(
            countries: List<Country>,
            test: Country.() -> Boolean): List<Country> // 增加了一个函数类型的参数test
    {
        val res = mutableListOf<Country>()
        for (c in countries) {
            if (c.test()) { // 直接调用test来进行筛选
                res.add(c)
            }
        }
        return res
    }
}


class CountryTest {
    fun isBigEuropeanCountry(country: Country): Boolean {
        return country.continent == "EU" && country.population > 10000
    }
}

fun countryFilterTest() {
    val countryApp = CountryApp()
    val countryTest = CountryTest()
    val countries = listOf(Country("China", "Asia", 1300000000),Country("France", "EU", 130000000))

    countryApp.filterCountries(countries, countryTest::isBigEuropeanCountry)
    var filterCountries = countryApp.filterCountries(countries, fun(country: Country): Boolean {
        return country.continent == "EU" && country.population > 10000
    })

    var filterCountries2 =   countryApp.filterCountries(countries) {
        this.continent == "EU" && this.population > 10000
    }

    val test: Country.() -> Boolean = { this.continent == "EU" && this.population > 10000 }
    var filterCountries3 = countryApp.filterCountries(countries,test)
    println("${filterCountries.size}  ${filterCountries2.size} ${filterCountries3.size}")
}

fun lambdaDef() {
    val sum0: (Int, Int) -> Int = { x: Int, y: Int ->
        x + y
    }
    val sum1 = { x: Int, y: Int ->
        x + y
    }

    val sum2: (Int, Int) -> Int = { x, y ->
        x + y
    }

    println(sum0(1, 1))
    println(sum1(1, 1))
    println(sum2(1, 1))
}

fun funInvoke() {
    fun foo(i: Int): () -> Unit = {
        print(i)
    }
    listOf(1, 2, 3).forEach { foo(it) }
    listOf(1, 2, 3).forEach { foo(it).invoke() }
    listOf(1, 2, 3).forEach { foo(it)() }
}

fun selfRunLambda() {
    { x: Int -> println(x) }(0)
}

fun curryLike() {
    fun <A, B> Array<A>.corresponds(that: Array<B>, p: (A, B) -> Boolean): Boolean {
        val i = this.iterator()
        val j = that.iterator()
        while (i.hasNext() && j.hasNext()) {
            if (!p(i.next(), j.next())) {
                return false
            }
        }
        return !i.hasNext() && !j.hasNext()
    }

    val a = arrayOf(1, 2, 3)
    val b = arrayOf(2, 3, 4)
    val c = arrayOf(2.0, 3.0, 4.0)
    println(a.corresponds(b) { x, y -> x + 1 == y })// true
    println(a.corresponds(b) { x, y -> x + 2 == y })// false
    println(a.corresponds(c) { x, y -> x + 1 == y.toInt() })
}

fun main(args: Array<String>) {
    countryFilterTest()
    funInvoke()
    selfRunLambda()
    curryLike()
}

