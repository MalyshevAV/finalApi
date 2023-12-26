package Specifications;

import io.qameta.allure.Description;
import org.testng.annotations.DataProvider;


public class GetPositivedataprovider {
  //  @DataProvider
    @Description("Позитивные тесты с использованием DataProvider")
    public static Object[][] positiveData() {
        return new Object[][]{
                {1},
                {5},
                {6},
                {100},
                {199},
                {200}
        };
    }
        @DataProvider
        @Description("Позитивные тесты с использованием DataProvider")
        public static Object[][] type() {
            return new Object[][]{
                    {0},
                    {2}
            };
    }

    @DataProvider
    @Description("Невалидный Type ")
    public static Object[][] negativeType() {
        return new Object[][]{
                {6},
                {-1},
                {100},
                {Integer.MAX_VALUE}
        };
    }
    @DataProvider
    @Description("Невалидный Type ")
    public static Object[][] negativeTypeString() {
        return new Object[][]{
                {"Select"},
                {"<script>alert( 'Hello world' );</script>"},
                {"select*FROM users"}
        };
    }
}
