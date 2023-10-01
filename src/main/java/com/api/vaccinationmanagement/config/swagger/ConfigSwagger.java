package com.api.vaccinationmanagement.config.swagger;

import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class ConfigSwagger {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // Thiết lập các server dùng để test api
                .servers(Lists.newArrayList(new Server().url("https://nqanvo-vaccination-management-api.azurewebsites.net"),new Server().url("http://localhost:8080")))
                // info
                .info(new Info().title("API - Vaccination management")
                        .description("<div>\n" +
                                "      <div>\n" +
                                "        <h3>Tài khoản / mật khẩu:</h3>\n" +
                                "        <ul>\n" +
                                "          <li>Admin: admin@gmail.com / 123Abc</li>\n" +
                                "          <li>Manager: manager@gmail.com / 123Abc</li>\n" +
                                "          <li>Employee: employee_a@gmail.com / 123Abc</li>\n" +
                                "          <li>Employee: employee_b@gmail.com / 123Abc</li>\n" +
                                "          <li>Employee: employee_c@gmail.com / 123Abc</li>\n" +
                                "        </ul>\n" +
                                "      </div>\n" +
                                "      <div>\n" +
                                "        <h3>Quyền khu vực:</h3>\n" +
                                "        <ul>\n" +
                                "          <li>Admin: Full</li>\n" +
                                "          <li>Manager: Full</li>\n" +
                                "          <li>Employee_a: Quyền cấp thành phố (City)</li>\n" +
                                "          <li>Employee_b: Quyền cấp quận/huyện (District)</li>\n" +
                                "          <li>Employee_c: Quyền cấp phường/xã (Wards)</li>\n" +
                                "        </ul>\n" +
                                "      </div>\n" +
                                "      <div>\n" +
                                "        <h3>Cấu trúc mã khu vực:</h3>\n" +
                                "        <ul>\n" +
                                "          <li>\n" +
                                "            City (0001 / TP HCM):\n" +
                                "            <ul>\n" +
                                "              <li>\n" +
                                "                District (0001-0001 / TP HCM - Quận 1):\n" +
                                "                <ul>\n" +
                                "                  <li>Wards (0001-0001-0001 / TP HCM - Quận 1 - Bến Nghé)</li>\n" +
                                "                  <li>Wards (0001-0001-0002 / TP HCM - Quận 1 - Bến Thành)</li>\n" +
                                "                </ul>\n" +
                                "              </li>\n" +
                                "            </ul>\n" +
                                "          </li>\n" +
                                "        </ul>\n" +
                                "      </div>\n" +
                                "    </div>")
                        .contact(new Contact()
                                .email("goldenv@gmail.com")
                                .name("Vo~"))
                        .version("1.0.0"));
    }
}
