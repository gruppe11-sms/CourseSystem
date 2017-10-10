package dk.group11.rolesystem.role

import org.springframework.web.bind.annotation.*


@RestController
class TestController(val roleRepository: RoleRepository) {

    @GetMapping("/api/hello")
    fun testGet(): String {
        return "hello world"
    }

    @GetMapping("/api/roles")
    fun getRoles(): Iterable<Role> {
        return roleRepository.findAll()
    }

    @PostMapping("/api/roles")
    fun createRole(@RequestBody role: Role): Role? {
        return roleRepository.save(role)
    }

    @GetMapping("/api/roles/{key}")
    fun getRole(@PathVariable key: String): Role {
        return roleRepository.findByKey(key)
    }
}