package com.funstart.funstartgames.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_USERS")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Campo [name] obrigatório")
	private String name;
	
	@Email(message = "O campo [email] deve conter um e-mail válido.")
	@NotBlank(message = "O campo [email] deve ser preenchido.")
	private String email;

	private String password;

}
