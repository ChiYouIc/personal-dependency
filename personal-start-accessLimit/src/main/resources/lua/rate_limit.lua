-- 缓存的 key
local key = KEYS[1]

-- 访问次数
local count = tonumber(ARGV[1])

-- 过期时间
local time = tonumber(ARGV[2])

-- 获取当前 key 对应的访问次数
local current = redis.call('get', key)

-- 如果当前访问次数大于预定值，则直接返回
if current and tonumber(current) > count then
    return tonumber(current)
end

-- 访问次数自增，如果 key 不存在，则初始化 0 再自增
current = redis.call('incr', key)

-- 如果当前访问次数为 1，则表示是一个新的 key，则配置过期时间
if tonumber(current) == 1 then
    redis.call('expire', key, time)
end

-- 返回当前的访问次数
return tonumber(current)
