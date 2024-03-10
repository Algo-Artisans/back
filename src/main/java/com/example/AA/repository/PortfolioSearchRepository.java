package com.example.AA.repository;

import com.example.AA.dto.PortfolioResDto;
import com.example.AA.entity.*;
import com.example.AA.entity.enumtype.HairName;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;


// 검색 저장소
@Slf4j
@Repository
public class PortfolioSearchRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public PortfolioSearchRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Portfolio> searchHairname(String hairName) { //아이롱펌

        QPortfolio portfolio = QPortfolio.portfolio;
        QPortfolioHairStyle qportfolioHairStyle = QPortfolioHairStyle.portfolioHairStyle;


        // hairName을 가진 hairStyleId를 가져온다.
        Long hairStyleId = queryFactory.
                select(QHairStyle.hairStyle.hairStyleId)
                .from(QHairStyle.hairStyle)
                .where(QHairStyle.hairStyle.hairName.eq(hairName))
                .fetchOne();

        // PortfolioHairStyles에서 portfolioId를 가져온다.
        List<Long> portfolioIds = queryFactory
                .selectDistinct(qportfolioHairStyle.portfolio.portfolioId)
                .from(qportfolioHairStyle)
                .where(qportfolioHairStyle.hairStyle.hairStyleId.eq(hairStyleId))
                .fetch();

        // in 절로 가져온 Portfolio의 id의 집합을 이용하여 Portfolio 객체를 가져온다.
        // 이 때, hairstyleportfolio 테이블을 fetchjoin으로 가져온다.
        return queryFactory
                .selectFrom(portfolio)
                .distinct()
                .leftJoin(portfolio.user).fetchJoin()
                .leftJoin(portfolio.portfolioHairStyles, qportfolioHairStyle).fetchJoin()
                .where(portfolio.portfolioId.in(portfolioIds))
                .fetch();

    }

}